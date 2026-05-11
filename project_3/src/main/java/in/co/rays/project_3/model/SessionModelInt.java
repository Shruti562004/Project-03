package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SessionDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface SessionModelInt {

	public long add(SessionDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(SessionDTO dto) throws ApplicationException;

	public void update(SessionDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(SessionDTO dto) throws ApplicationException;

	public List search(SessionDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public SessionDTO findByPK(long pk) throws ApplicationException;

	public SessionDTO findByName(String name) throws ApplicationException;
}