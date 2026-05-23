package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.VideoDTO;
import in.co.rays.project_3.dto.VideoDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface VideoModelInt {

	public long add(VideoDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(VideoDTO dto) throws ApplicationException;

	public void update(VideoDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(VideoDTO dto) throws ApplicationException;

	public List search(VideoDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public VideoDTO findByPK(long pk) throws ApplicationException;

	public VideoDTO findByCategory(String category) throws ApplicationException;
}