package in.co.rays.project_3.test;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.CateringDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CateringModelHibImp;
import in.co.rays.project_3.model.CateringModelInt;


public class CateringModelTest {
	public static CateringModelInt model=new CateringModelHibImp();
	
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
		
	//addTest();
		//updateTest();
	//	deleteTest();
		
		listTest();
	}

	private static void listTest() throws ApplicationException {
	  CateringDTO dto=new CateringDTO();
	  List list=new ArrayList();
	  list=model.list(1,10);
	  if (list.size() < 0) {
			System.out.println("list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			
			dto=(CateringDTO) it.next();
			
			System.out.println(dto.getId());
			System.out.println(dto.getVendorName());
			System.out.println(dto.getMenuType());
			System.out.println(dto.getCost());
		}
	  
	  
	}

	private static void deleteTest() throws ApplicationException {
		CateringDTO dto=new CateringDTO();
		dto.setId(1L);
		model.delete(dto);
		
	}

	private static void updateTest() throws ApplicationException, DuplicateRecordException {
		CateringDTO dto=new CateringDTO();
		dto.setId(1L);
		dto.setVendorName("hello");
		dto.setMenuType("miss");
		dto.setCost("you");
		 dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     model.update(dto);
		
	}

	private static void addTest() throws ApplicationException, DuplicateRecordException {
	CateringDTO dto= new CateringDTO();
	
	//dto.setId(1L);
	dto.setVendorName("how");
	dto.setMenuType("are");
	dto.setCost("you");
	 dto.setCreatedBy("Admin");
     dto.setModifiedBy("Admin");
     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
     long pk = model.add(dto); 
	}
	
	
	

}
