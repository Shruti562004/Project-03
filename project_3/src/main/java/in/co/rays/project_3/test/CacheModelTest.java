package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.project_3.dto.CacheDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CacheModelHibImp;
import in.co.rays.project_3.model.CacheModelInt;

public class CacheModelTest {
	public static CacheModelHibImp model= new CacheModelHibImp();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
		addTest();
		
	}
	
	private static void addTest() throws ApplicationException, DuplicateRecordException {
		CacheDTO dto= new CacheDTO();
		
		//dto.setId(1L);
		dto.setCode("how");
		dto.setName("are");
		dto.setValue("you");
		dto.setStatus("hh");
		 dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     long pk = model.add(dto); 
		}
		

}
