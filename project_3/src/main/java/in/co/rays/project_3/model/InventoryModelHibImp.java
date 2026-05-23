package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.InventoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class  InventoryModelHibImp implements InventoryModelInt{
	
public long add(InventoryDTO dto) throws ApplicationException, DuplicateRecordException {
	
	Session session=null;
	Transaction tx=null;
	InventoryModelInt Inventorymod = ModelFactory.getInstance().getInventoryModel();
	InventoryDTO Inventorydto = Inventorymod.findBySupplier(dto.getSupplier());
	if(Inventorydto!=null) {
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
public void delete(InventoryDTO dto) throws ApplicationException {
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
public void update(InventoryDTO dto) throws ApplicationException, DuplicateRecordException {
	Session session=null;
	Transaction tx=null;
	InventoryModelInt Inventorymod = ModelFactory.getInstance().getInventoryModel();
	InventoryDTO Inventorydto = Inventorymod.findBySupplier(dto.getSupplier());
	if(Inventorydto!=null && Inventorydto.getId()!=dto.getId() ) {
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
		Criteria criteria=session.createCriteria(InventoryDTO.class);
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
public List search(InventoryDTO dto) throws ApplicationException {
	// TODO Auto-generated method stub
	return search(dto, 0, 0);
}

@Override
public List search(InventoryDTO dto, int pageNo, int pageSize) throws ApplicationException {
	Session session=null;
	List list=null;
	try {
		session=HibDataSource.getSession();
		Criteria criteria=session.createCriteria(InventoryDTO.class);
		if(dto!=null) {
			
			if(dto.getId() > 0) {
			    criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getSupplier()!=null && dto.getSupplier().length()>0) {
				
				criteria.add(Restrictions.like("supplier", dto.getSupplier() + "%"));
			}
			
			if (dto.getName()!= null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
				
			}
			
			
			if (dto.getStock() > 0) {
				criteria.add(Restrictions.eq("stock", dto.getStock()));
				
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
public InventoryDTO findByPK(long pk) throws ApplicationException {
Session session=null;
InventoryDTO dto= null;
try {
	session= HibDataSource.getSession();
	dto=(InventoryDTO) session.get(InventoryDTO.class, pk);
	
	
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
public InventoryDTO findBySupplier(String supplier) throws ApplicationException {
	   Session session = null;
	    InventoryDTO dto = null;

	    try {

	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(InventoryDTO.class);

	        criteria.add(Restrictions.eq("supplier", supplier.trim()));

	        List list = criteria.list();

	        if (list.size() > 0) {
	            dto = (InventoryDTO) list.get(0);
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
