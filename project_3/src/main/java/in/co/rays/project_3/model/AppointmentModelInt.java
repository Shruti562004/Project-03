package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AppointmentDTO;
import in.co.rays.project_3.dto.CateringDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface AppointmentModelInt {

	public long add(AppointmentDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(AppointmentDTO dto) throws ApplicationException;

	public void update(AppointmentDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(AppointmentDTO dto) throws ApplicationException;

	public List search(AppointmentDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public AppointmentDTO findByPK(long pk) throws ApplicationException;

	public AppointmentDTO findByName(String name) throws ApplicationException;
}