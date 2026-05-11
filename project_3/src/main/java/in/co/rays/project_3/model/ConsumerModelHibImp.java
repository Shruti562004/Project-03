package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.dto.ConsumerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ConsumerModelHibImp implements ConsumerModelInt {

	@Override
	public long add(ConsumerDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		ConsumerModelInt Consumermod = ModelFactory.getInstance().getConsumerModel();
		ConsumerDTO Consumerdto = Consumermod.findByCode(dto.getCode());
		if(Consumerdto!=null) {
			throw new DuplicateRecordException("consumer already exist");
		}
		long pk = 0;
		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Student Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	@Override
	public void delete(ConsumerDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in  Delete" + e.getMessage());
		} finally {
			session.close();
		}

		
	}

	@Override
	public void update(ConsumerDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
				Session session = null;
				Transaction tx = null;
				ConsumerModelInt Consumermod = ModelFactory.getInstance().getConsumerModel();
				ConsumerDTO Consumerdto = Consumermod.findByCode(dto.getCode());
				if(Consumerdto!=null && Consumerdto.getId()!=dto.getId()) {
					throw new DuplicateRecordException("code already exist");
				}
				try {
					session = HibDataSource.getSession();
					tx = session.beginTransaction();
					session.update(dto);

					tx.commit();

				} catch (HibernateException e) {

					if (tx != null) {
						tx.rollback();
						throw new ApplicationException("Exception in Student Update" + e.getMessage());
					}
				} finally {
					session.close();
				}
		
	}

	@Override
	public List list(int pageNo,int pageSize) throws ApplicationException {
	Session session=null;
	List list=null;
	
	try {
		session=HibDataSource.getSession();
		
		Criteria criteria=session.createCriteria(ConsumerDTO.class);
		if (pageSize > 0) {
		    int index = (pageNo - 1) * pageSize;
		    criteria.setFirstResult(index);
		    criteria.setMaxResults(pageSize);
		}
		list=criteria.list();
		
		
	}
	
	catch (HibernateException e) {

	
	} finally {
		session.close();
	}
	
	return list;
	}

	@Override
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	@Override
	public List search(ConsumerDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(ConsumerDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session=null;
		List list=null;
		
		try {
			session=HibDataSource.getSession();
			
			Criteria criteria=session.createCriteria(ConsumerDTO.class);
			if(dto!=null) {
				
			if( dto.getId()!=null ) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
				if(dto.getCode()!=null && dto.getCode().length()>0) {
				 criteria.add(Restrictions.like("code", dto.getCode() + "%"));
				
			}
				
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if(dto.getStatus()!=null && dto.getStatus().length()>0) {
					 criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
					
				}
				
			}	
			if (pageSize > 0) {
			    int index = (pageNo - 1) * pageSize;
			    criteria.setFirstResult(index);
			    criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
			}
			catch (HibernateException e) {

				
			} finally {
				session.close();
			}
			
			return list;
			}
	

	@Override
	public ConsumerDTO findByPK(long pk) throws ApplicationException {
		Session session=null;
		ConsumerDTO dto=null;
		try {
			session=HibDataSource.getSession();
	
			dto=(ConsumerDTO) session.get(ConsumerDTO.class, pk);
	
				
			}
		catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting subject by pk");
		} finally {
			session.close();
		}

		return dto;
	
	}

	@Override
	public ConsumerDTO findByCode(String code) throws ApplicationException {
	Session session=null;
	ConsumerDTO dto=null;
	
	try {
		session=HibDataSource.getSession();
		Criteria criteria = session.createCriteria(ConsumerDTO.class);
		criteria.add(Restrictions.eq("code", code));
		
		List list=criteria.list();
		
		if(list.size()==1){
			dto=(ConsumerDTO) list.get(0);
		}
	} catch (HibernateException e) {
       
        throw new ApplicationException(
                "Exception in getting Student by email " + e.getMessage());

    } finally {
        session.close();
    }
	return dto;
}

	

}
