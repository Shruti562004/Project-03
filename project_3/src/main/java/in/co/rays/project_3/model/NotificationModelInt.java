package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.NotificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface NotificationModelInt {

	public long add(NotificationDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(NotificationDTO dto) throws ApplicationException;

	public void update(NotificationDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(NotificationDTO dto) throws ApplicationException;

	public List search(NotificationDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public NotificationDTO findByPK(long pk) throws ApplicationException;

	public NotificationDTO findByName(String name) throws ApplicationException;
}