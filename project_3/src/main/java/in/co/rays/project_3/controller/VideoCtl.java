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
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.VideoModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Video functionality ctl.to perform add,delete ,update operation
 * 
 * @author Shruti Rathore
 *
 */

@WebServlet(urlPatterns = { "/ctl/VideoCtl" })
public class VideoCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(VideoCtl.class);

	protected void preload(HttpServletRequest request) {

		VideoModelInt model = ModelFactory.getInstance().getVideoModel();

		try {
			List list = model.list();

			request.setAttribute("VideoList", list);

		} catch (Exception e) {
			log.error(e);
		}
	}

	protected boolean validate(HttpServletRequest request) {

		log.debug("VideoCtl validate Start");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {

			request.setAttribute("title",
					PropertyReader.getValue("error.require", "Title"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("title"))) {

			request.setAttribute("title",
					PropertyReader.getValue("error.name", "Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("category"))) {

			request.setAttribute("category",
					PropertyReader.getValue("error.require", "Category"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("duration"))) {

			request.setAttribute("duration",
					PropertyReader.getValue("error.require", "Duration"));
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("duration"))) {

			request.setAttribute("duration",
					PropertyReader.getValue("error.integer", "Duration"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("views"))) {

			request.setAttribute("views",
					PropertyReader.getValue("error.require", "Views"));
			pass = false;

		} else if (!DataValidator.isInteger(request.getParameter("views"))) {

			request.setAttribute("views",
					PropertyReader.getValue("error.integer", "Views"));
			pass = false;
		}

		log.debug("VideoCtl validate End");

		return pass;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Video ctl do get start");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		VideoModelInt model = ModelFactory.getInstance().getVideoModel();

		if (id > 0 || op != null) {
			VideoDTO dto;
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
		log.debug("Video ctl do get end");
	}

	/**
	 * Submit logic inside it
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Video ctl do post start");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		VideoModelInt model = ModelFactory.getInstance().getVideoModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			VideoDTO dto = (VideoDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("category  already exists", request);
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
			VideoDTO dto = (VideoDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.VIDEO_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.VIDEO_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.VIDEO_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("Video ctl do post end");

	}

	@Override
	protected String getView() {

		return ORSView.VIDEO_VIEW;
	}

}
