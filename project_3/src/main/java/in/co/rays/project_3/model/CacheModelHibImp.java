package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CacheDTO;
import in.co.rays.project_3.dto.CacheDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class CacheModelHibImp implements CacheModelInt{
	
public long add(CacheDTO dto) throws ApplicationException, DuplicateRecordException {
	
	Session session=null;
	Transaction tx=null;
	CacheModelInt Cachemod = ModelFactory.getInstance().getCacheModel();
	CacheDTO Cachedto = Cachemod.findByName(dto.getName());
	if(Cachedto!=null) {
		throw new DuplicateRecordException("name already exist");
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
        throw new ApplicationException("Exception in Notification Add " + e.getMessage());

    } finally {
        if (session != null) {
            session.close();
        }
    }

    return dto.getId();
	
}

@Override
public void delete(CacheDTO dto) throws ApplicationException {
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
        throw new ApplicationException("Exception in Notification Add " + e.getMessage());

    } finally {
        if (session != null) {
            session.close();
        }
    }
	
}

@Override
public void update(CacheDTO dto) throws ApplicationException, DuplicateRecordException {
	Session session=null;
	Transaction tx=null;
	CacheModelInt Cachemod = ModelFactory.getInstance().getCacheModel();
	CacheDTO Cachedto = Cachemod.findByName(dto.getName());
	if(Cachedto!=null && Cachedto.getId()!=dto.getId() ) {
		throw new DuplicateRecordException("name  already exist");
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
        throw new ApplicationException("Exception in Notification Add " + e.getMessage());

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
		Criteria criteria=session.createCriteria(CacheDTO.class);
		if(pageSize>0) {
			pageNo = (pageNo - 1) * pageSize;
		 criteria.setFirstResult(pageNo);
		 criteria.setMaxResults(pageSize);
			
		}
		list=criteria.list();
	}
	catch (HibernateException e) {
		
		  
	    e.printStackTrace();
	    throw new ApplicationException("Exception in Notification Add " + e.getMessage());

	} finally {
	    if (session != null) {
	        session.close();
	    }
	}
	return list;
}

@Override
public List search(CacheDTO dto) throws ApplicationException {
	// TODO Auto-generated method stub
	return search(dto, 0, 0);
}

@Override
public List search(CacheDTO dto, int pageNo, int pageSize) throws ApplicationException {
	Session session=null;
	List list=null;
	try {
		session=HibDataSource.getSession();
		Criteria criteria=session.createCriteria(CacheDTO.class);
		if(dto!=null) {
			if(dto.getCode()!=null && dto.getCode().length()>0) {
				
				criteria.add(Restrictions.like("code", dto.getCode() + "%"));
			}
			
			if (dto.getName()!= null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
				
			}
			
			
			if (dto.getValue() != null && dto.getValue().length() > 0) {
				criteria.add(Restrictions.like("value", dto.getValue() + "%"));
				
			}
			
			if(dto.getId()!=null && dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
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
    throw new ApplicationException("Exception in Notification Add " + e.getMessage());

} finally {
    if (session != null) {
        session.close();
    }
}
	
	return list;
}

@Override
public CacheDTO findByPK(long pk) throws ApplicationException {
Session session=null;
CacheDTO dto= null;
try {
	session= HibDataSource.getSession();
	dto=(CacheDTO) session.get(CacheDTO.class, pk);
	
	
}

catch (HibernateException e) {
	
  
    e.printStackTrace();
    throw new ApplicationException("Exception in Notification Add " + e.getMessage());

} finally {
    if (session != null) {
        session.close();
    }
}
return dto;

}
@Override
public CacheDTO findByName(String name) throws ApplicationException {

    Session session = null;
    CacheDTO dto = null;

    try {

        session = HibDataSource.getSession();

        Criteria criteria = session.createCriteria(CacheDTO.class);

        criteria.add(Restrictions.eq("name", name.trim()));

        List list = criteria.list();

        if (list.size() > 0) {
            dto = (CacheDTO) list.get(0);
        }

    } catch (HibernateException e) {

        e.printStackTrace();
        throw new ApplicationException("Exception in findByName " + e.getMessage());

    } finally {

        if (session != null) {
            session.close();
        }
    }

    return dto;
}
}
