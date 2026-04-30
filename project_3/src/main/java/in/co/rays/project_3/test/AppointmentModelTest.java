package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.project_3.dto.AppointmentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.AppointmentModelHibImp;
import in.co.rays.project_3.model.AppointmentModelInt;
import in.co.rays.project_3.model.CateringModelHibImp;
import in.co.rays.project_3.model.CateringModelInt;

public class AppointmentModelTest {
	
	public static AppointmentModelInt model=new AppointmentModelHibImp();
	public static void main(String[] args) throws ParseException, ApplicationException, DuplicateRecordException {
		
		
		addTest();
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

}
