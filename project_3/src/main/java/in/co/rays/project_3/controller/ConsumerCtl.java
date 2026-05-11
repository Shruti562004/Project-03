package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ConsumerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Consumer functionality ctl. To perform add,delete ,update operation
 * 
 * @author Shruti Rathore
 * 
 */

@WebServlet(urlPatterns = { "/ctl/ConsumerCtl" })
public class ConsumerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ConsumerCtl.class);

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.require", "code"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("code"))) {
			request.setAttribute("code", "code must contain alphabets only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("group"))) {
			request.setAttribute("group", PropertyReader.getValue("error.require", "group"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("group"))) {
			request.setAttribute("group", "group must contain aphabets only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "name must contain aphabets only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}
		 else if (!DataValidator.isName(request.getParameter("status"))) {
				request.setAttribute("status", "status must contain aphabets only");
				pass = false;
			}
		
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		ConsumerDTO dto = new ConsumerDTO();

		System.out.println(request.getParameter("mobileNo"));

		dto.setCode(request.getParameter("code"));
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("city"));
		System.out.println(request.getParameter("address"));
		System.out.println(request.getParameter("state"));
		System.out.println(request.getParameter("mobileNo"));
		dto.setGroup(request.getParameter("group"));
		dto.setName(request.getParameter("name"));
		dto.setStatus(request.getParameter("status"));
		

		populateBean(dto, request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		ConsumerModelInt model = ModelFactory.getInstance().getConsumerModel();

		if (id > 0 || op != null) {
			ConsumerDTO dto;

			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		ConsumerModelInt model = ModelFactory.getInstance().getConsumerModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ConsumerDTO dto = (ConsumerDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("Consumer add" + dto + "id...." + id);
					// long pk
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("code Already Exists", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CONSUMER_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CONSUMER_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.CONSUMER_VIEW;
	}

}
