package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HistoryDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface HistoryModelInt {

	public long add(HistoryDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(HistoryDTO dto) throws ApplicationException;

	public void update(HistoryDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(HistoryDTO dto) throws ApplicationException;

	public List search(HistoryDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public HistoryDTO findByPK(long pk) throws ApplicationException;

	public HistoryDTO findByCode(String code) throws ApplicationException;
}