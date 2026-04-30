package in.co.rays.project_3.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import in.co.rays.project_3.dto.CacheDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.util.HibDataSource;

public class CacheModelHibImp {
	
public long add(CacheDTO dto) throws ApplicationException {
	
	Session session=null;
	Transaction tx=null;
	
	
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

}
