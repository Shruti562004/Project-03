package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HospitalDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface HospitalModelInt {

	public long add(HospitalDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(HospitalDTO dto) throws ApplicationException;

	public void update(HospitalDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(HospitalDTO dto) throws ApplicationException;

	public List search(HospitalDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public HospitalDTO findByPK(long pk) throws ApplicationException;

	public HospitalDTO findByDisease(String disease) throws ApplicationException;
}