package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.HospitalDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class  HospitalModelHibImp implements HospitalModelInt{
	
public long add(HospitalDTO dto) throws ApplicationException, DuplicateRecordException {
	
	Session session=null;
	Transaction tx=null;
	HospitalModelInt Hospitalmod = ModelFactory.getInstance().getHospitalModel();
	HospitalDTO Hospitaldto = Hospitalmod.findByDisease(dto.getDisease());
	if(Hospitaldto!=null) {
		throw new DuplicateRecordException("disease already exist");
	}
	
	try {
		session= HibDataSource.getSession();
		 tx=session.beginTransaction();
		 session.save(dto);
		 tx.commit();
		 
 
		
	}
	
	catch (HibernateException e) {
    	
        if (tx != null) {
            tx.rollback();
        }
        e.printStackTrace();
        throw new ApplicationException("Exception in  Add " + e.getMessage());

    } finally {
        if (session != null) {
            session.close();
        }
    }

    return dto.getId();
	
}

@Override
public void delete(HospitalDTO dto) throws ApplicationException {
	Session session=null;
	Transaction tx=null;
	
	
	try {
		session= HibDataSource.getSession();
		 tx=session.beginTransaction();
		 session.delete(dto);
		 tx.commit();
		 
 
		
	}
	
catch (HibernateException e) {
    	
        if (tx != null) {
            tx.rollback();
        }
        e.printStackTrace();
        throw new ApplicationException("Exception in  Add " + e.getMessage());

    } finally {
        if (session != null) {
            session.close();
        }
    }
	
}

@Override
public void update(HospitalDTO dto) throws ApplicationException, DuplicateRecordException {
	Session session=null;
	Transaction tx=null;
	HospitalModelInt Hospitalmod = ModelFactory.getInstance().getHospitalModel();
	HospitalDTO Hospitaldto = Hospitalmod.findByDisease(dto.getDisease());
	if(Hospitaldto!=null &&dto.getId()!=Hospitaldto.getId()) {
		throw new DuplicateRecordException("disease already exist");
	}
	
	
	try {
		session= HibDataSource.getSession();
		 tx=session.beginTransaction();
		 session.update(dto);
		 tx.commit();
		 
 
		
	}
	
catch (HibernateException e) {
    	
        if (tx != null) {
            tx.rollback();
        }
        e.printStackTrace();
        throw new ApplicationException("Exception in  Add " + e.getMessage());

    } finally {
        if (session != null) {
            session.close();
        }
    }
	
}

@Override
public List list() throws ApplicationException {
	// TODO Auto-generated method stub
	return list(0, 0);
}

@Override
public List list(int pageNo, int pageSize) throws ApplicationException {
	Session session=null;
	List list=null;
	try {
		
		session=HibDataSource.getSession();
		Criteria criteria=session.createCriteria(HospitalDTO.class);
		if(pageSize>0) {
			pageNo = (pageNo - 1) * pageSize;
		 criteria.setFirstResult(pageNo);
		 criteria.setMaxResults(pageSize);
			
		}
		list=criteria.list();
	}
	catch (JDBCConnectionException e) {

		e.printStackTrace();

	throw new ApplicationException("Database Server is Down");
	} catch (HibernateException e) {

		throw new ApplicationException("Exception in Student search");
	} finally {

		session.close();

	}

	return list;
}

@Override
public List search(HospitalDTO dto) throws ApplicationException {
	// TODO Auto-generated method stub
	return search(dto, 0, 0);
}

@Override
public List search(HospitalDTO dto, int pageNo, int pageSize) throws ApplicationException {
	Session session=null;
	List list=null;
	try {
		session=HibDataSource.getSession();
		Criteria criteria=session.createCriteria(HospitalDTO.class);
		if(dto!=null) {
			
			if(dto.getId() > 0) {
			    criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getDocName()!=null && dto.getDocName().length()>0) {
				
				criteria.add(Restrictions.like("docName", dto.getDocName() + "%"));
			}
			
			if (dto.getName()!= null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
				
			}
			
			if (dto.getDisease()!= null && dto.getDisease().length() > 0) {
				criteria.add(Restrictions.like("disease", dto.getDisease() + "%"));
				
			}
			
		}
		
		if(pageSize>0) {
			pageNo = (pageNo - 1) * pageSize;
			criteria.setFirstResult(pageNo);
			criteria.setMaxResults(pageSize);
			
		}
		
		list=criteria.list();
		
	}

	catch (HibernateException e) {

		e.printStackTrace();

		throw new ApplicationException("Database Server is Down");

	} finally {

		session.close();

	}

	return list;
}



@Override
public HospitalDTO findByPK(long pk) throws ApplicationException {
Session session=null;
HospitalDTO dto= null;
try {
	session= HibDataSource.getSession();
	dto=(HospitalDTO) session.get(HospitalDTO.class, pk);
	
	
}

catch (HibernateException e) {
	
  
    e.printStackTrace();
    throw new ApplicationException("Exception in  Add " + e.getMessage());

} finally {
    if (session != null) {
        session.close();
    }
}
return dto;

}


@Override
public HospitalDTO findByDisease(String disease) throws ApplicationException {
	   Session session = null;
	    HospitalDTO dto = null;

	    try {

	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(HospitalDTO.class);

	        criteria.add(Restrictions.eq("disease", disease.trim()));

	        List list = criteria.list();

	        if (list.size() > 0) {
	            dto = (HospitalDTO) list.get(0);
	        }

	    } catch (HibernateException e) {

	        e.printStackTrace();
	        throw new ApplicationException("Exception in  " + e.getMessage());

	    } finally {

	        if (session != null) {
	            session.close();
	        }
	    }

	    return dto;
}
}
