package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PlacementDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PlacementModelInt;
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

@WebServlet(urlPatterns = { "/ctl/PlacementCtl" })
public class PlacementCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PlacementCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("course ctl validate start");

		boolean pass = true;
		
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", " name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("coordinates"))) {
			request.setAttribute("coordinates", PropertyReader.getValue("error.require", "coordinates "));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("coordinates"))) {
			request.setAttribute("coordinates", PropertyReader.getValue("error.name", " coordinates"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("scale"))) {
			request.setAttribute("scale", PropertyReader.getValue("error.require", "scale"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("scale"))) {
			request.setAttribute("scale", PropertyReader.getValue("error.name", " scale"));
			pass = false;
		}
		 
		if (DataValidator.isNull(request.getParameter("rotation"))) {
			request.setAttribute("rotation", PropertyReader.getValue("error.require", "rotation"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("rotation"))) {
			request.setAttribute("rotation", PropertyReader.getValue("error.name", " rotation"));
			pass = false;
		}
		log.debug("course ctl validate end");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("course ctl populate bean start");

		PlacementDTO dto = new PlacementDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setCoordinates(DataUtility.getString(request.getParameter("coordinates")));
		dto.setScale(DataUtility.getString(request.getParameter("scale")));
		dto.setRotation(DataUtility.getString(request.getParameter("rotation")));

		populateBean(dto, request);

		log.debug("ctl populate bean end");

		return dto;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("course ctl do get start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		PlacementModelInt model = ModelFactory.getInstance().getPlacementModel();

		if (id > 0 || op != null) {
			PlacementDTO dto;
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
		PlacementModelInt model = ModelFactory.getInstance().getPlacementModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PlacementDTO dto = (PlacementDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("name  already exists", request);
					}
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Name already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			PlacementDTO dto = (PlacementDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PLACEMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PLACEMENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.PLACEMENT_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("course ctl do post end");

	}

	@Override
	protected String getView() {

		return ORSView.PLACEMENT_VIEW;
	}

}
