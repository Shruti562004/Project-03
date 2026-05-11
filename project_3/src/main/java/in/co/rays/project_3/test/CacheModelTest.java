package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.AppointmentDTO;
import in.co.rays.project_3.dto.CacheDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CacheModelHibImp;
import in.co.rays.project_3.model.CacheModelInt;

public class CacheModelTest {
	public static CacheModelHibImp model= new CacheModelHibImp();
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
		
		//addTest();
		//deleteTest();
		//updateTest();
		//findByPkTest();
		//findByNameTest();
		//listTest();
		searchList();
		
	}
	private static void searchList() throws ApplicationException {
		CacheDTO dto=new CacheDTO();
		List list=new ArrayList();
		dto.setCode("y");
		list=model.search(dto, 0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()) {
		dto=(CacheDTO) it.next();
			
			System.out.println(dto.getCode());
			System.out.println(dto.getName());
			System.out.println(dto.getValue());
			System.out.println(dto.getStatus());
			
		}
	}
	private static void listTest() throws ApplicationException {
	CacheDTO dto=new CacheDTO();
	List list=new ArrayList();
	list=model.list(0, 0);
	
	Iterator it=list.iterator();
	while(it.hasNext()) {
		dto=(CacheDTO) it.next();
		
		System.out.println(dto.getCode());
		System.out.println(dto.getName());
		System.out.println(dto.getValue());
		System.out.println(dto.getStatus());
	}
	
		
	}

	private static void findByNameTest() throws ApplicationException {
		CacheDTO dto=new CacheDTO();
		dto=model.findByName("are");
		System.out.println(dto.getCode());
		System.out.println(dto.getName());
		System.out.println(dto.getValue());
		System.out.println(dto.getStatus());
		
	}

	private static void findByPkTest() throws ApplicationException {
		CacheDTO dto=new CacheDTO();
		dto=model.findByPK(1);
		System.out.println(dto.getCode());
		System.out.println(dto.getName());
		System.out.println(dto.getValue());
		System.out.println(dto.getStatus());
		
		
	}

	private static void updateTest() throws ApplicationException, DuplicateRecordException {
CacheDTO dto= new CacheDTO();
		
	dto.setId(1L);
		dto.setCode("uoofg");
		dto.setName("vvf");
		dto.setValue("you");
		dto.setStatus("hh");
		 dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     model.update(dto); 
		
	}

	private static void deleteTest() throws ApplicationException {
		CacheDTO dto= new CacheDTO();
		
		dto.setId(3L);
		model.delete(dto);
		
	}

	private static void addTest() throws ApplicationException, DuplicateRecordException {
		CacheDTO dto= new CacheDTO();
		
		//dto.setId(1L);
		dto.setCode("qqqqqqqqqqqqqqqqqq");
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
