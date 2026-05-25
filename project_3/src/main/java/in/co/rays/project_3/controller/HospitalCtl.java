package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HospitalDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.HospitalModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Hospital functionality ctl.to perform add,delete ,update operation
 * 
 * @author Shruti Rathore
 *
 */

@WebServlet(urlPatterns = { "/ctl/HospitalCtl" })
public class HospitalCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(HospitalCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("Hospital ctl validate start");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.require", "code"));
			pass = false;
		} 
		else if (!DataValidator.isName(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.name", " code"));
			pass = false;
		
			
		}
			
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} 
		else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", " name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("disease"))) {
			request.setAttribute("disease", PropertyReader.getValue("error.require", "disease"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("disease"))) {
			request.setAttribute("disease", PropertyReader.getValue("error.name", " disease"));
			pass = false;
		}
		 
		if (DataValidator.isNull(request.getParameter("docName"))) {
			request.setAttribute("docName", PropertyReader.getValue("error.require", "docName"));
			pass = false;
		}
		
		else if (!DataValidator.isName(request.getParameter("docName"))) {
			request.setAttribute("docName", PropertyReader.getValue("error.name", " docName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("bill"))) {
			request.setAttribute("bill", PropertyReader.getValue("error.require", "bill"));
			pass = false;
		}
		
		else if (!DataValidator.isInteger(request.getParameter("bill"))) {
			request.setAttribute("bill", PropertyReader.getValue("error.name", " bill"));
			pass = false;
		}
		
		log.debug("Hospital ctl validate end");
	
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

	    log.debug("HospitalCtl populateDTO Start");

	    HospitalDTO dto = new HospitalDTO();

	    dto.setId(DataUtility.getLong(request.getParameter("id")));
	    dto.setName(DataUtility.getString(request.getParameter("name")));
	    dto.setDisease(DataUtility.getString(request.getParameter("disease")));
	    dto.setDocName(DataUtility.getString(request.getParameter("docName")));
	    dto.setBill(DataUtility.getInt(request.getParameter("bill")));

	    populateBean(dto, request);

	    log.debug("HospitalCtl populateDTO End");

	    return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Hospital ctl do get start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		HospitalModelInt model = ModelFactory.getInstance().getHospitalModel();

		if (id > 0 || op != null) {
			HospitalDTO dto;
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
		HospitalModelInt model = ModelFactory.getInstance().getHospitalModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HospitalDTO dto = (HospitalDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					dto.setId(id);
					ServletUtility.setSuccessMessage("Data Successfully Update", request);
					ServletUtility.setDto(dto, request);
				} else {

					try {
						model.add(dto);
						dto.setId(0L);
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
			HospitalDTO dto = (HospitalDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.HOSPITAL_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.HOSPITAL_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.HOSPITAL_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("Hospital ctl do post end");

	}

	@Override
	protected String getView() {

		return ORSView.HOSPITAL_VIEW;
	}

}
