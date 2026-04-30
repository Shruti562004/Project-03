package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import in.co.rays.project_3.dto.AppointmentDTO;
import in.co.rays.project_3.dto.CateringDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class AppointmentModelHibImp  implements AppointmentModelInt{
	
	public  long add(AppointmentDTO dto) throws ApplicationException {
		Session session=null;
		Transaction tx=null;
		long pk=0;
		
		/*CateringDTO existDto=findByName(dto.getVendorName());
		if(existDto!=null) {
			throw new DuplicateElementException("vendor already exist");
		}*/
		
		session=HibDataSource.getSession();
		try {
			tx = session.beginTransaction();
			System.out.println(dto.getName());
			session.save(dto);
			pk=dto.getId();
			tx.commit();
		}
		catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new ApplicationException("Exception in Catering add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}


	@Override
	public void delete(AppointmentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AppointmentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(AppointmentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(AppointmentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CateringDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CateringDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
