package in.co.rays.project_3.model;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mchange.util.DuplicateElementException;

import in.co.rays.project_3.dto.CateringDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * @author Shruti Rathore
 * Catering Model
 *
 */
public class CateringModelHibImp  implements CateringModelInt{
	
	public  long add(CateringDTO dto) throws ApplicationException {
		Session session=null;
		Transaction tx=null;
		long pk=0;
		
		CateringDTO existDto=findByVendorName(dto.getVendorName());
		if(existDto!=null) {
			throw new DuplicateElementException("vendor already exist");
		}
		
		try {
			session=HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk=dto.getId();
			tx.commit();
		}
		catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Catering add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	@Override
	public void delete(CateringDTO dto) throws ApplicationException {
		Session session=null;
		Transaction tx=null;
		try {
			session=HibDataSource.getSession();
			tx=session.beginTransaction();
			session.delete(dto);
			tx.commit();
		}
			
			catch (HibernateException e) {

				if (tx != null) {
					tx.rollback();
				}

				throw new ApplicationException("Exception in Catering update " + e.getMessage());

			} finally {
				session.close();
			}
		}

	@Override
	public void update(CateringDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session=null;
		Transaction tx=null;
		CateringDTO existDto=findByVendorName(dto.getVendorName());
		
		if(existDto!=null && existDto.getId()!=dto.getId()) {
			throw new DuplicateRecordException("vendor already exits");
		}
		
			try {
				session=HibDataSource.getSession();
				tx=session.beginTransaction();
				session.update(dto);
				tx.commit();
			}
			 catch (HibernateException e) {

					if (tx != null) {
						tx.rollback();
					}

					throw new ApplicationException("Exception in Catering update " + e.getMessage());

				} finally {
					session.close();
				}
			}


	
	
	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
	Session session=null;
	List list=null;
	
	try {
		session.beginTransaction();
		Criteria criteria=session.createCriteria(CateringDTO.class);
		if(pageSize>0) {
			criteria.setFirstResult(pageNo);
			criteria.setMaxResults(pageSize);
			
		}
		list=criteria.list();
		
	
	}
	
	catch (HibernateException e) {

		throw new ApplicationException("Exception in Catering list");

	} finally {
		session.close();
	}

	return list;
}

	@Override
	public List search(CateringDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(CateringDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CateringDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CateringDTO findByVendorName(String vendorName) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
