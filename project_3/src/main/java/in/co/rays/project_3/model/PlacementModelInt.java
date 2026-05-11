package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PlacementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Placement model
 * @author Shruti Rathore
 *
 */
public interface PlacementModelInt {
public long add(PlacementDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(PlacementDTO dto)throws ApplicationException;
public void update(PlacementDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(PlacementDTO dto)throws ApplicationException;
public List search(PlacementDTO dto,int pageNo,int pageSize)throws ApplicationException;
public PlacementDTO findByPK(long pk)throws ApplicationException;
public PlacementDTO findByName(String name)throws ApplicationException;
}
