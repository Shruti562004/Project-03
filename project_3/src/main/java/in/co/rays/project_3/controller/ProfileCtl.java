package in.co.rays.project_3.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ProfileModelHibImp;
import in.co.rays.project_3.model.ProfileModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ProfileCtl", urlPatterns = { "/ctl/ProfileCtl" })

public class ProfileCtl extends BaseCtl {

	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";
	public static final String OP_DELETE = "Delete";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_RESET = "Reset";

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.require", "code "));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.name", " code"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", " name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("number"))) {
			request.setAttribute("number", PropertyReader.getValue("error.require", "number"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("number"))) {
			request.setAttribute("number", PropertyReader.getValue("error.name", " number"));
			pass = false;
		}
		 
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("code"))) {
			request.setAttribute("status", PropertyReader.getValue("error.name", " status"));
			pass = false;
		}
	
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ProfileDTO dto = new ProfileDTO();

		long id = DataUtility.getLong(request.getParameter("id"));

		// IMPORTANT FIX
		if (id > 0) {
			dto.setId(id);
		}

		dto.setCode(DataUtility.getString(request.getParameter("code")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setNumber(DataUtility.getString(request.getParameter("number")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		ProfileModelInt model = new ProfileModelHibImp();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || OP_SAVE.equalsIgnoreCase(op)
				|| OP_UPDATE.equalsIgnoreCase(op)) {

			ProfileDTO dto;

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

		ProfileModelInt model = new ProfileModelHibImp();

		long id = DataUtility.getLong(request.getParameter("id"));

		ProfileDTO dto = (ProfileDTO) populateDTO(request);

		if (OP_SAVE.equalsIgnoreCase(op)) {

			try {

				long pk = model.add(dto);

				dto.setId(0L);

				ServletUtility.setSuccessMessage(
						"Profile Added Successfully", request);

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
						"Profile Updated Successfully", request);

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
						ORSView.PROFILE_LIST_CTL,
						request, response);

				return;

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.PROFILE_CTL,
					request, response);

			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.PROFILE_LIST_CTL,
					request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.PROFILE_VIEW;
	}
}