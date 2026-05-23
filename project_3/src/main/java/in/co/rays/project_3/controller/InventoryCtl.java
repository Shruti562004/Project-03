package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.InventoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CollegeModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.InventoryModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 *Inventory functionality CRUD operation
 * 
 * @author Shruti Rathore
 *
 */
@WebServlet(urlPatterns = { "/ctl/InventoryCtl" })
public class InventoryCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(InventoryCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

	InventoryModelInt model = ModelFactory.getInstance().getInventoryModel();
		try {
			List l = model.list();
			request.setAttribute("inventoryList", l);
		}
		catch (ApplicationException e) {
	        System.out.println("Exception in preload");
	        e.printStackTrace();
	        ServletUtility.setErrorMessage("Database Server is Down", request);
	    }
	}


	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("InventoryCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		String email = request.getParameter("emailId");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "name"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("stock"))) {
			request.setAttribute("stock", PropertyReader.getValue("error.require", "stock"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("stock"))) {
			request.setAttribute("stock", PropertyReader.getValue("error.name", "stock"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("supplier"))) {
			request.setAttribute("supplier", PropertyReader.getValue("error.require", "supplier"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("supplier"))) {
			request.setAttribute("supplier", "Please Enter Alphabet");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "price"));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "price"));
			pass = false;
			
		}
		
		log.debug("InventoryCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("InventoryCtl Method populatebean Started");

		InventoryDTO dto = new InventoryDTO   ();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setStock(DataUtility.getInt(request.getParameter("stock")));

		dto.setSupplier(DataUtility.getString(request.getParameter("supplier")));

		
		dto.setPrice(DataUtility.getLong(request.getParameter("price")));

		populateBean(dto, request);

		log.debug("InventoryCtl Method populatebean Ended");

		return dto;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("InventoryCtl Method doGet Started");

//		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get model

		InventoryModelInt model = ModelFactory.getInstance().getInventoryModel();

		if (id > 0) {
			InventoryDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("InventoryCtl Method doGett Ended");
		ServletUtility.forward(getView(), request, response);
		
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("InventoryCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		InventoryModelInt model = ModelFactory.getInstance().getInventoryModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			InventoryDTO dto = (InventoryDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {
					try {

						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Inventory already exists", request);
					}

				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Inventory Email Id already exists", request);
			}

		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			InventoryDTO dto = (InventoryDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.INVENTORY_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("InventoryCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.INVENTORY_VIEW;
	}

}
