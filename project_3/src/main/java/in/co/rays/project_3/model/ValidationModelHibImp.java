package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import in.co.rays.project_3.dto.ValidationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ValidationModelHibImp implements ValidationModelInt {
	
	public long add(ValidationDTO dto) throws ApplicationException {
		
		
		Session session= null;
		Transaction tx=null;
		int pk=0;
		
		try {
			
			
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
		     session.save(dto);
		     tx.commit();
		     
			
		}
		
		catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in  Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	@Override
	public void delete(ValidationDTO dto) throws ApplicationException {
		Session session=null;
		Transaction tx=null;
		try {
			
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.delete(dto);
			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in  Add " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ValidationDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session=null;
		Transaction tx=null;
		try {
			
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in  Add " + e.getMessage());
		} finally {
			session.close();
		}
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
	public List search(ValidationDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(ValidationDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationDTO findByPK(long pk) throws ApplicationException {
		Sess
		return null;
	}

	@Override
	public ValidationDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
		
	
	
	

}
