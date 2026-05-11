package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AppointmentDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.AppointmentModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Appointment functionality ctl.to perform add,delete ,update operation
 * 
 * @author Shruti Rathore
 *
 */

@WebServlet(urlPatterns = { "/ctl/AppointmentCtl" })
public class AppointmentCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(AppointmentCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("Appointment ctl validate start");

		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} 
		else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", " name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "date"));
			pass = false;
		}
		else if (!DataValidator.isDate(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.name", " date"));
			pass = false;
		}
		 
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}
		
		else if (!DataValidator.isName(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.name", " status"));
			pass = false;
		}
		log.debug("Appointment ctl validate end");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("Appointment ctl populate bean start");

		AppointmentDTO dto = new AppointmentDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
	
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setDate(DataUtility.getDate(request.getParameter("date")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));
		populateBean(dto, request);

		log.debug("ctl populate bean end");

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Appointment ctl do get start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		AppointmentModelInt model = ModelFactory.getInstance().getAppointmentModel();

		if (id > 0 || op != null) {
			AppointmentDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
		log.debug(" ctl do get end");
	}

	/**
	 * Submit logic inside it
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug(" do post start");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));
		AppointmentModelInt model = ModelFactory.getInstance().getAppointmentModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			AppointmentDTO dto = (AppointmentDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					dto.setId(id);
					ServletUtility.setSuccessMessage("Data Successfully Update", request);
					ServletUtility.setDto(dto, request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data Successfully saved", request);
						ServletUtility.setDto(dto, request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("code  already exists", request);
					}
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			AppointmentDTO dto = (AppointmentDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.APPOINTMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.APPOINTMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.APPOINTMENT_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("Appointment ctl do post end");

	}

	@Override
	protected String getView() {

		return ORSView.APPOINTMENT_VIEW;
	}

}
