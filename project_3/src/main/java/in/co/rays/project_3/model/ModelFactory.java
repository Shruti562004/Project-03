 package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Shruti Rathore
 * 
 * 
 *a
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static final CacheModelHibImp CacheModel = null;
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	/**
	 * Make Default Constructor Private
	 */
	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) 			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
	
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}
	
	//usecase
	public CateringModelInt getCateringModel() {
		CateringModelInt cateringModel = (CateringModelInt) modelCache.get("cateringModel");
		if (cateringModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				cateringModel = new CateringModelHibImp();

			}
			
			modelCache.put("collegeModel", cateringModel);
		}
		return cateringModel;
	}
	



public AppointmentModelInt getAppointmentModel() {
	AppointmentModelInt AppointmentModel = (AppointmentModelInt) modelCache.get("AppointmentModel");
	if (AppointmentModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			AppointmentModel = new AppointmentModelHibImp();

		}
		
		modelCache.put("AppointmentModel", AppointmentModel);
	}
	return AppointmentModel;
}




public NotificationModelInt getNotificationModel() {
	NotificationModelInt NotificationModel = (NotificationModelInt) modelCache.get("NotificationModel");
	if (NotificationModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			NotificationModel =  new NotificationModelHibImp();

		}
		
		modelCache.put("NotificationModel", NotificationModel);
	}
	return NotificationModel;
}

public ValidationModelInt getValidationModel() {
	ValidationModelInt ValidationModel = (ValidationModelInt) modelCache.get("ValidationModel");
	if (ValidationModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			ValidationModel = new ValidationModelHibImp();

		}
		
		modelCache.put("ValidationModel", ValidationModel);
	}
	return ValidationModel;
}

public CacheModelInt getCacheModel() {
	CacheModelInt CacheModel = (CacheModelInt) modelCache.get("CacheModel");
	if (CacheModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			CacheModel = new CacheModelHibImp();

		}
		
		modelCache.put("CacheModel", CacheModel);
	}
	return CacheModel;
}


public ConsumerModelInt getConsumerModel() {
	ConsumerModelInt ConsumerModel = (ConsumerModelInt) modelCache.get("ConsumerModel");
	if (ConsumerModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			ConsumerModel = new  ConsumerModelHibImp();

		}
		
		modelCache.put("ConsumerModel", ConsumerModel);
	}
	return ConsumerModel;
}
public SessionModelInt getSessionModel() {
	SessionModelInt SessionModel = (SessionModelInt) modelCache.get("SessionModel");
	if (SessionModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			SessionModel = new  SessionModelHibImp();

		}
		
		modelCache.put("SessionModel", SessionModel);
	}
	return SessionModel;
}
public PlacementModelInt getPlacementModel() {
	PlacementModelInt PlacementModel = (PlacementModelInt) modelCache.get("PlacementModel");
	if (PlacementModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			PlacementModel = new PlacementModelHibImp();

		}
		
		modelCache.put("PlacementModel", PlacementModel);
	}
	return PlacementModel;
}


public ProfileModelInt getProfileModel() {
	ProfileModelInt  ProfileModel= (ProfileModelInt) modelCache.get("ProfileModel");
	if (ProfileModel == null) {
		if ("Hibernate".equals(DATABASE)) {
			ProfileModel = new ProfileModelHibImp();

		}
		
		modelCache.put("ProfileModel", ProfileModel);
	}
	return ProfileModel;
}

}

