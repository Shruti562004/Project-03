package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.project_3.dto.ValidationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ValidationModelHibImp;
import in.co.rays.project_3.model.ValidationModelInt;

public class ValidationModelTest {
	
	
	public static ValidationModelInt model=new ValidationModelHibImp();
	
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		//addTest();
		//deleteTest();
		updateTest();
	}


	private static void updateTest() throws ApplicationException, DuplicateRecordException {
		ValidationDTO dto=new ValidationDTO();
		dto.setId(1L);
		dto.setCode("hii");
		dto.setName("q");
		dto.setRule("gg");
		dto.setStatus("hii");
		
		dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	    model.update(dto);
		
	}


	private static void deleteTest() throws ApplicationException {
		ValidationDTO dto=new ValidationDTO();
		dto.setId(2L);
		model.delete(dto);
		
	}


	private static void addTest() throws ApplicationException, DuplicateRecordException {
		ValidationDTO dto=new ValidationDTO();
		dto.setCode("dffddf");
		dto.setName("q");
		dto.setRule("gg");
		dto.setStatus("hii");
		
		dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     long pk=model.add(dto);
	     System.out.println("Data inserted with PK: " + pk);
	     
	}
	
	
	
	

}
