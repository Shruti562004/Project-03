package in.co.rays.project_3.test;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.AppointmentDTO;
import in.co.rays.project_3.dto.NotificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AppointmentModelHibImp;
import in.co.rays.project_3.model.AppointmentModelInt;


public class AppointmentModelTest {
	
	public static AppointmentModelInt model=new AppointmentModelHibImp();
	public static void main(String[] args) throws ParseException, ApplicationException, DuplicateRecordException {
		
		
		//addTest();
		//deleteTest();
		//updateTest();
		//findByPkTest();
		//findByName();
		//listTest();
		searchList();
	}

	private static void searchList() throws ApplicationException {
		AppointmentDTO dto=new AppointmentDTO();
		List list=new ArrayList();
		dto.setName("c");
		list=model.search(dto, 0, 0);
		Iterator it=list.iterator();
		while(it.hasNext()) {
			dto=(AppointmentDTO) it.next();

			System.out.println(dto.getName());
			System.out.println(dto.getDate());
			System.out.println(dto.getStatus());
			
		}
		
	}

	private static void findByName() throws ApplicationException {
		AppointmentDTO dto=new AppointmentDTO();
		dto=model.findByName("chinuu");
		
		System.out.println(dto.getName());
		System.out.println(dto.getDate());
		System.out.println(dto.getStatus());
		
	}

	private static void updateTest() throws ParseException, ApplicationException, DuplicateRecordException {
		AppointmentDTO dto=new AppointmentDTO();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		dto.setId(2L);
		dto.setName("chinuu");
		dto.setDate(sdf.parse("20-06-2003"));
		dto.setStatus("pending");
		dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	      model.update(dto);
		
	}

	private static void addTest() throws ParseException, ApplicationException, DuplicateRecordException {
		AppointmentDTO dto=new AppointmentDTO();
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		dto.setName("sandeep");
		dto.setDate(sdf.parse("20-06-2003"));
		dto.setStatus("pending");
		dto.setCreatedBy("Admin");
	     dto.setModifiedBy("Admin");
	     dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
	     dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
	     long pk = model.add(dto);
	}
	
	
	private static void deleteTest() throws ApplicationException {
		AppointmentDTO dto=new AppointmentDTO();
		dto.setId(1L);
		model.delete(dto);
		
	}
	
	private static void findByPkTest() throws ApplicationException {
		AppointmentDTO dto=new AppointmentDTO();
		dto=model.findByPK(3L);
		
		System.out.println(dto.getName());
		System.out.println(dto.getDate());
		System.out.println(dto.getStatus());
		
	}
	

	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		AppointmentDTO dto = null;
		List list = new ArrayList();
		 list=model.list(0,0);
		System.out.println(list.size());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (AppointmentDTO) it.next();
			
			System.out.println(dto.getName());
			System.out.println(dto.getDate());
			System.out.println(dto.getStatus());
		}
	}
	


}
