package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SessionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.SessionModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 *Session functionality ctl.to show list ofSession
 * 
 * @author Shruti Rathore
 *
 */
@WebServlet(name = "SessionListCtl", urlPatterns = { "/ctl/SessionListCtl" })
public class SessionListCtl extends BaseCtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SessionListCtl.class);

	protected void preload(HttpServletRequest request) {

		SessionModelInt model = ModelFactory.getInstance().getSessionModel();

		try {
			List list = model.list();
			request.setAttribute("RollNo", list);

		} catch (Exception e) {

		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("course ctl populate bean start");

		SessionDTO dto = new SessionDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCode(DataUtility.getString(request.getParameter("code")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setLoginTime(DataUtility.getDate(request.getParameter("loginTime")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));
		populateBean(dto, request);

		log.debug("ctl populate bean end");

		return dto;

	}

	/**
	 * ContainsDisplaylogics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SessionDTO dto = (SessionDTO) populateDTO(request);

		List list = null;
		List next = null;

		SessionModelInt model = ModelFactory.getInstance().getSessionModel();
		try {
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		if (next == null || next.size() == 0) {
			request.setAttribute("nextListSize", 0);

		} else {
			request.setAttribute("nextListSize", next.size());
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("SessionListCtl doGet End");

	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SessionListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SessionDTO dto = (SessionDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		SessionModelInt model = ModelFactory.getInstance().getSessionModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SESSION_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.SESSION_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SESSION_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					SessionDTO deletebean = new SessionDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);
					}

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			dto = (SessionDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);// calling search

			ServletUtility.setDto(dto, request);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("SessionListCtl doPost End");
	}

	@Override
	protected String getView() {

		return ORSView.SESSION_LIST_VIEW;
	}
}
