package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Consumer model
 * @author Shruti Rathore
 *
 */
public interface ConsumerModelInt {
public long add(ConsumerDTO dto)throws ApplicationException,DuplicateRecordException;
public void delete(ConsumerDTO dto)throws ApplicationException;
public void update(ConsumerDTO dto)throws ApplicationException,DuplicateRecordException;
public List list()throws ApplicationException;
public List list(int pageNo,int pageSize)throws ApplicationException;
public List search(ConsumerDTO dto)throws ApplicationException;
public List search(ConsumerDTO dto,int pageNo,int pageSize)throws ApplicationException;
public ConsumerDTO findByPK(long pk)throws ApplicationException;
public ConsumerDTO findByCode(String code)throws ApplicationException;
}
