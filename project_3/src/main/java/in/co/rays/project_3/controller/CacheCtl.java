package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CacheDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.CacheModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * course functionality ctl.to perform add,delete ,update operation
 * 
 * @author Shruti Rathore
 *
 */

@WebServlet(urlPatterns = { "/ctl/CacheCtl" })
public class CacheCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CacheCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("course ctl validate start");

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
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", " name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("value"))) {
			request.setAttribute("value", PropertyReader.getValue("error.require", "value"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("value"))) {
			request.setAttribute("value", PropertyReader.getValue("error.name", " value"));
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
		log.debug("course ctl validate end");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("course ctl populate bean start");

		CacheDTO dto = new CacheDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCode(DataUtility.getString(request.getParameter("code")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setValue(DataUtility.getString(request.getParameter("value")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));
		populateBean(dto, request);

		log.debug("ctl populate bean end");

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("course ctl do get start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		CacheModelInt model = ModelFactory.getInstance().getCacheModel();

		if (id > 0 || op != null) {
			CacheDTO dto;
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
		CacheModelInt model = ModelFactory.getInstance().getCacheModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CacheDTO dto = (CacheDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("name  already exists", request);
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
			CacheDTO dto = (CacheDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.CACHE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CACHE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CACHE_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("course ctl do post end");

	}

	@Override
	protected String getView() {

		return ORSView.CACHE_VIEW;
	}

}
