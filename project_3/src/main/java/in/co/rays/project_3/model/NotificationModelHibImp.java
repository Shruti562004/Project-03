package in.co.rays.project_3.model;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.CollegeDTO;
import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.dto.NotificationDTO;
import in.co.rays.project_3.dto.FacultyDTO;
import in.co.rays.project_3.dto.NotificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class NotificationModelHibImp implements NotificationModelInt{

	
	
	public long add(NotificationDTO dto) throws ApplicationException, DuplicateRecordException {
	    Session session = null;
	    Transaction tx = null;
		NotificationModelInt Notificationmod = ModelFactory.getInstance().getNotificationModel();
		NotificationDTO Notificationdto = Notificationmod.findByName(dto.getName());
		if(Notificationdto!=null) {
			throw new DuplicateRecordException("name already exist");
		}
	    try {
	        session = HibDataSource.getSession();
	        tx = session.beginTransaction();

	        session.save(dto);

	        tx.commit();

	    } catch (HibernateException e) {
	    	
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        throw new ApplicationException("Exception in Notification Add " + e.getMessage());

	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return dto.getId();
	}

	@Override
	public void delete(NotificationDTO dto) throws ApplicationException {
		Session session=null;
		Transaction tx=null;
		
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.delete(dto);
			tx.commit();
		}
		catch (HibernateException e) {
			tx.rollback();
		}
		
		finally {
			session.close();
		}
		
	}
	@Override
	public void update(NotificationDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session=null;
		Transaction tx=null;
		
		NotificationModelInt Notificationmod = ModelFactory.getInstance().getNotificationModel();
		NotificationDTO Notificationdto = Notificationmod.findByName(dto.getName());
		if(Notificationdto!=null && Notificationdto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("name already exist");
		}
		try {
			
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.update(dto);
			tx.commit();
		}
		
		catch (HibernateException e) {
			tx.rollback();
		}
		
		finally {
			session.close();
		}
		
	}

	@Override
	public NotificationDTO findByPK(long pk) throws ApplicationException {
		Session session=null;
		NotificationDTO dto=null;
		try
		{
              	session=HibDataSource.getSession();
			
		dto=(NotificationDTO) session.get(NotificationDTO.class, pk);
		}
		catch (HibernateException e) {
		e.printStackTrace();
		}
		
		finally {
			session.close();
		}
		return dto;
		
	}

	@Override
	public NotificationDTO findByName(String name) throws ApplicationException {
		Session session=null;
		NotificationDTO dto=null;
		try
		{
	session=HibDataSource.getSession();
	
	Criteria criteria=session.createCriteria(NotificationDTO.class);
	criteria.add(Restrictions.eq("name", name));
	List list=criteria.list();
	if(list.size()>0) {
		dto = (NotificationDTO) list.get(0);
	}
	
	}
		
		catch (HibernateException e) {
			e.printStackTrace();
			}
			
			finally {
				session.close();
			}
			return dto;
			
		}
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(NotificationDTO .class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  College list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(NotificationDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(NotificationDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(NotificationDTO.class);
			if (dto!=null && dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));

			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}
			if (dto.getCode() != null && dto.getCode().length() > 0) {
				criteria.add(Restrictions.like("code", dto.getCode() + "%"));
			}
			if (dto.getType() != null && dto.getType().length() > 0) {
				criteria.add(Restrictions.like("type", dto.getType() + "%"));
			}
			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in college search");
		} finally {
			session.close();
		}
		return list;
	}

	public List list() throws ApplicationException {
		
		return list(0,0);
	}

	
}




