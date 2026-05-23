package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.VideoDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.VideoModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Video list functionality ctl.Toperform search and delete,show list operation
 * 
 * @author Shruti Rathore
 *
 */
@WebServlet(name = "VideoListCtl", urlPatterns = { "/ctl/VideoListCtl" })
public class VideoListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger("VideoListCtl.class");

	protected void preload(HttpServletRequest request) {

		VideoModelInt model = ModelFactory.getInstance().getVideoModel();

		try {
			List list = model.list();

			request.setAttribute("VideoList", list);

		} catch (Exception e) {
			log.error(e);
		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("VideoCtl populateDTO Start");

		VideoDTO dto = new VideoDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setTitle(DataUtility.getString(request.getParameter("title")));

		dto.setCategory(DataUtility.getString(request.getParameter("category")));

		dto.setDuration(DataUtility.getInt(request.getParameter("duration")));

		dto.setViews(DataUtility.getInt(request.getParameter("views")));

		populateBean(dto, request);

		log.debug("VideoCtl populateDTO End");

		return dto;
	}
	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Video ctl do get start");

		List list = null;
		List next = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		VideoDTO dto = (VideoDTO) populateDTO(request);

		VideoModelInt model = ModelFactory.getInstance().getVideoModel();

		try {
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			ServletUtility.setList(list, request);

			next = model.search(dto, pageNo + 1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Video ctl do get end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Video List do post start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		VideoDTO dto = (VideoDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		VideoModelInt model = ModelFactory.getInstance().getVideoModel();

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.VIDEO_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.VIDEO_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.VIDEO_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					VideoDTO deletebean = new VideoDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}

				} else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}
			dto = (VideoDTO) populateDTO(request);
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", "0");
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
		} catch (Exception e) {

			e.printStackTrace();
		}
		log.debug("Video List do post end");
	}

	@Override
	protected String getView() {

		return ORSView.VIDEO_LIST_VIEW;
	}

}
