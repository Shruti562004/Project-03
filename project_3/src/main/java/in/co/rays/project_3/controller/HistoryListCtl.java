package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HistoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.HistoryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "HistoryListCtl", urlPatterns = { "/ctl/HistoryListCtl" })

public class HistoryListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(HistoryListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

		try {

			HistoryModelInt model = ModelFactory.getInstance().getHistoryModel();

			List nameList = model.list();

			request.setAttribute("nameList", nameList);

		} catch (Exception e) {

			log.error(e);

		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("HistoryListCtl populateDTO Start");

		HistoryDTO dto = new HistoryDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setCode(DataUtility.getString(request.getParameter("code")));

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setLoginTime(DataUtility.getDate(request.getParameter("loginTime")));

		dto.setStatus(DataUtility.getString(request.getParameter("status")));

		populateBean(dto, request);

		log.debug("HistoryListCtl populateDTO End");

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0)
				? DataUtility.getInt(PropertyReader.getValue("page.size"))
				: pageSize;

		HistoryDTO dto = (HistoryDTO) populateDTO(request);

		List list = null;

		List next = null;

		HistoryModelInt model = ModelFactory.getInstance().getHistoryModel();

		request.setAttribute("nextListSize", 0);

		try {

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);

			return;
		}

		if (list == null || list.size() == 0) {

			ServletUtility.setErrorMessage("No record found", request);
		}

		if (next != null && next.size() > 0) {

			request.setAttribute("nextListSize", next.size());
		}

		ServletUtility.setList(list, request);

		ServletUtility.setPageNo(pageNo, request);

		ServletUtility.setPageSize(pageSize, request);

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;

		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0)
				? DataUtility.getInt(PropertyReader.getValue("page.size"))
				: pageSize;

		HistoryDTO dto = (HistoryDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		HistoryModelInt model = ModelFactory.getInstance().getHistoryModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op)
					|| OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op)
						&& pageNo > 1) {

					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HISTORY_CTL, request,
						response);

				return;

			} else if (OP_RESET.equalsIgnoreCase(op)
					|| OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HISTORY_LIST_CTL,
						request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					for (String id : ids) {

						HistoryDTO deleteBean = new HistoryDTO();

						deleteBean.setId(DataUtility.getLong(id));

						model.delete(deleteBean);
					}

					ServletUtility.setSuccessMessage(
							"Data Successfully Deleted!", request);

				} else {

					ServletUtility.setErrorMessage(
							"Select at least one record", request);
				}
			}

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);

			ServletUtility.setList(list, request);

			if ((list == null || list.size() == 0)
					&& !OP_DELETE.equalsIgnoreCase(op)) {

				ServletUtility.setErrorMessage("No record found", request);
			}

			request.setAttribute("nextListSize",
					(next != null) ? next.size() : 0);

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);
		}
	}

	@Override
	protected String getView() {

		return ORSView.HISTORY_LIST_VIEW;
	}
}