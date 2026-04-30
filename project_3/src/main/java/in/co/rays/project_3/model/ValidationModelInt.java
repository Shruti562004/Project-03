package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ValidationDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface  ValidationModelInt {

	public long add(ValidationDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(ValidationDTO dto) throws ApplicationException;

	public void update(ValidationDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(ValidationDTO dto) throws ApplicationException;

	public List search(ValidationDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public ValidationDTO findByPK(long pk) throws ApplicationException;

	public ValidationDTO findByName(String name) throws ApplicationException;
}