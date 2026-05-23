package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.ProfileDTO;
import in.co.rays.project_3.dto.StudentDTO;
import in.co.rays.project_3.dto.ProfileDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ProfileModelHibImp implements ProfileModelInt {

	@Override
	public long add(ProfileDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		ProfileModelInt Profilemod = ModelFactory.getInstance().getProfileModel();
		ProfileDTO Profiledto = Profilemod.findByCode(dto.getCode());
		if(Profiledto!=null) {
			throw new DuplicateRecordException("Profile already exist");
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
			throw new ApplicationException("Exception in  Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	@Override
	public void delete(ProfileDTO dto) throws ApplicationException {
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
	public void update(ProfileDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
				Session session = null;
				Transaction tx = null;
				ProfileModelInt Profilemod = ModelFactory.getInstance().getProfileModel();
				ProfileDTO Profiledto = Profilemod.findByCode(dto.getCode());
				if(Profiledto!=null && Profiledto.getId()!=dto.getId()) {
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
		
		Criteria criteria=session.createCriteria(ProfileDTO.class);
		if (pageSize > 0) {
		    int index = (pageNo - 1) * pageSize;
		    criteria.setFirstResult(index);
		    criteria.setMaxResults(pageSize);
		}
		list=criteria.list();
		
		
	}
	
catch (JDBCConnectionException e) {
		throw e;
	} catch (HibernateException e) {

		throw new ApplicationException("Exception : Exception in  role list");
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
	public List search(ProfileDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(ProfileDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session=null;
		List list=null;
		
		try {
			session=HibDataSource.getSession();
			
			Criteria criteria=session.createCriteria(ProfileDTO.class);
			if(dto!=null) {
				
			if( dto.getId()!=null && dto.getId()>0 ) {
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
	public ProfileDTO findByPK(long pk) throws ApplicationException {
		Session session=null;
		ProfileDTO dto=null;
		try {
			session=HibDataSource.getSession();
	
			dto=(ProfileDTO) session.get(ProfileDTO.class, pk);
	
				
			}
		catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting subject by pk");
		} finally {
			session.close();
		}

		return dto;
	
	}

	@Override
	public ProfileDTO findByCode(String code) throws ApplicationException {
	Session session=null;
	ProfileDTO dto=null;
	
	try {
		session=HibDataSource.getSession();
		Criteria criteria = session.createCriteria(ProfileDTO.class);
		criteria.add(Restrictions.eq("code", code));
		
		List list=criteria.list();
		
		if(list.size()==1){
			dto=(ProfileDTO) list.get(0);
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
