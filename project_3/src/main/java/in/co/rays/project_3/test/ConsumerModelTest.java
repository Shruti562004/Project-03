package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.ConsumerDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ConsumerModelHibImp;
import in.co.rays.project_3.model.ConsumerModelInt;


public class ConsumerModelTest {
	
	public static ConsumerModelInt model=new ConsumerModelHibImp();
	public static void main(String[] args) throws ParseException, ApplicationException, DuplicateRecordException {
		
		
	//addTest();
		//deleteTest();
		//updateTest();
		//findByPkTest();
		//findByCode();
		//listTest();
		searchList();
	}

	private static void searchList() throws ApplicationException {
		ConsumerDTO dto=new ConsumerDTO();
		List list=new ArrayList();
	dto.setCode("p");
		list=model.search(dto, 0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			dto=(ConsumerDTO) it.next();
			System.out.println(dto.getId());
			System.out.println(dto.getCode());
			System.out.println(dto.getGroup());
			System.out.println(dto.getName());
			
			System.out.println(dto.getStatus());
			
		}
		
	}

	private static void findByCode() throws ApplicationException {
		ConsumerDTO dto=new ConsumerDTO();
		dto=model.findByCode("book");
		System.out.println(dto.getId());
		System.out.println(dto.getCode());
		System.out.println(dto.getGroup());
		System.out.println(dto.getName());
		
		System.out.println(dto.getStatus());
		
	}

	private static void updateTest() throws ParseException, ApplicationException, DuplicateRecordException {
		ConsumerDTO dto=new ConsumerDTO();
		
		dto.setId(2L);
         dto.setCode("book");
		
		dto.setGroup("save");
		dto.setName("gggf");
		dto.setStatus("erf");
		dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	      model.update(dto);
		
	}

	private static void addTest() throws ParseException, ApplicationException, DuplicateRecordException {
		ConsumerDTO dto=new ConsumerDTO();
	
		dto.setCode("hg");
		
		dto.setGroup("save");
		dto.setName("gggf");
		dto.setStatus("erf");
		dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     long pk = model.add(dto);
	}
	
	
	private static void deleteTest() throws ApplicationException {
		ConsumerDTO dto=new ConsumerDTO();
		dto.setId(3L);
		model.delete(dto);
		
	}
	
	private static void findByPkTest() throws ApplicationException {
		ConsumerDTO dto=new ConsumerDTO();
		dto=model.findByPK(2L);
		System.out.println(dto.getId());
		System.out.println(dto.getCode());
		System.out.println(dto.getGroup());
		System.out.println(dto.getName());
		
		System.out.println(dto.getStatus());
		
	}
	

	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		ConsumerDTO dto = null;
		List list = new ArrayList();
		 list=model.list(0,0);
		System.out.println(list.size());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (ConsumerDTO) it.next();
			
			System.out.println(dto.getId());
			System.out.println(dto.getCode());
			System.out.println(dto.getGroup());
			System.out.println(dto.getName());
			
			System.out.println(dto.getStatus());
		}
	}
	


}
