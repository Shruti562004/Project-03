package in.co.rays.project_3.model;

import java.util.List;


import in.co.rays.project_3.dto.CacheDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface CacheModelInt {

	public long add(CacheDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(CacheDTO dto) throws ApplicationException;

	public void update(CacheDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(CacheDTO dto) throws ApplicationException;

	public List search(CacheDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public CacheDTO findByPK(long pk) throws ApplicationException;

	public CacheDTO findByName(String name) throws ApplicationException;
}