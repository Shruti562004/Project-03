package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.MarksheetDTO;
import in.co.rays.project_3.dto.NotificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

import in.co.rays.project_3.model.NotificationModelHibImp;
import in.co.rays.project_3.model.NotificationModelInt;

public class NotificationModelTest {

	public static NotificationModelInt model = (NotificationModelInt) new NotificationModelHibImp();

	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {

		//addTest();
		//updateTest();
		//deleteTest();
		//findByPkTest();
		//findByName();
		searchTest();
		//listTest();
	}

	private static void findByName() throws ApplicationException {
		NotificationDTO dto=model.findByName("hello");
		
		System.out.println(dto.getCode());
		System.out.println(dto.getName());
		System.out.println(dto.getType());
		System.out.println(dto.getStatus());
		
		
	}

	private static void findByPkTest() throws ApplicationException {
		NotificationDTO dto=new NotificationDTO();
		dto=model.findByPK(2L);
		System.out.println(dto.getCode());
		System.out.println(dto.getName());
		System.out.println(dto.getType());
		System.out.println(dto.getStatus());
		
	}

	private static void deleteTest() throws ApplicationException {
		NotificationDTO dto=new NotificationDTO();
		dto.setId(3l);
		model.delete(dto);
		
	}

	private static void updateTest() throws ApplicationException, DuplicateRecordException {
		NotificationDTO dto=new NotificationDTO();
		
		dto.setCode("tyyt");
		dto.setName("ioo");
		dto.setType("yupiee");

		dto.setStatus("fail");

		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setId(2L);
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(dto);
		
	}

	private static void addTest() throws ApplicationException, DuplicateRecordException {
		NotificationDTO dto = new NotificationDTO();

		dto.setCode("uuuuu");
		dto.setName("ioo");
		dto.setType("yupiee");

		dto.setStatus("fail");

		dto.setCreatedBy("admin");
		dto.setModifiedBy("admin");
		dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long id = model.add(dto);
		System.out.println("Inserted ID: " + id);

	}

	public static void listTest() throws ApplicationException {
		// TODO Auto-generated method stub
		NotificationDTO dto = null;
		List list = new ArrayList();
		 list=model.list(0,0);
		System.out.println(list.size());
		Iterator it = list.iterator();
		while (it.hasNext()) {
			dto = (NotificationDTO) it.next();
			System.out.println(dto.getCode());
			System.out.println(dto.getName());
			System.out.println(dto.getType());
			System.out.println(dto.getStatus());
		}
	}

	public static void searchTest() throws ApplicationException {
		// TODO Auto-generated method stub
		NotificationDTO dto1 = new NotificationDTO();
		dto1.setId(1L);
		dto1.setName("h");
		ArrayList<NotificationDTO> a = (ArrayList<NotificationDTO>) model.search(dto1, 0, 0);
		for (NotificationDTO dto : a) {

			System.out.println(dto.getCode());
			System.out.println(dto.getName());
			System.out.println(dto.getType());
			System.out.println(dto.getStatus());
		}
	}
}
