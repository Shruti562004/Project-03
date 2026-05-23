package in.co.rays.project_3.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.SessionModelHibImp;
import in.co.rays.project_3.model.SessionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "SessionCtl", urlPatterns = { "/ctl/SessionCtl" })

public class SessionCtl extends BaseCtl {

	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";
	public static final String OP_DELETE = "Delete";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_RESET = "Reset";

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("code"))) {
			request.setAttribute("code",
					PropertyReader.getValue("error.require", "Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name",
					PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("loginTime"))) {
			request.setAttribute("loginTime",
					PropertyReader.getValue("error.require", "Login Time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status",
					PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SessionDTO dto = new SessionDTO();

		long id = DataUtility.getLong(request.getParameter("id"));

		// IMPORTANT FIX
		if (id > 0) {
			dto.setId(id);
		}

		dto.setCode(DataUtility.getString(request.getParameter("code")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setLoginTime(DataUtility.getDate(request.getParameter("loginTime")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		SessionModelInt model = new SessionModelHibImp();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || OP_SAVE.equalsIgnoreCase(op)
				|| OP_UPDATE.equalsIgnoreCase(op)) {

			SessionDTO dto;

			try {

				dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		SessionModelInt model = new SessionModelHibImp();

		long id = DataUtility.getLong(request.getParameter("id"));

		SessionDTO dto = (SessionDTO) populateDTO(request);

		if (OP_SAVE.equalsIgnoreCase(op)) {

			try {

				long pk = model.add(dto);
                    
				dto.setId(0L);

				ServletUtility.setSuccessMessage(
						"Session Added Successfully", request);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;

			}catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage(e.getMessage(), request);

				ServletUtility.forward(getView(), request, response);

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			try {

				model.update(dto);

				ServletUtility.setSuccessMessage(
						"Session Updated Successfully", request);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage(e.getMessage(), request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			try {

				model.delete(dto);

				ServletUtility.redirect(
						ORSView.SESSION_LIST_CTL,
						request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.SESSION_CTL,
					request, response);

			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.SESSION_LIST_CTL,
					request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.SESSION_VIEW;
	}
}