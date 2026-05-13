package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.HistoryDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class HistoryModelHibImp  implements HistoryModelInt{
	
	public  long add(HistoryDTO dto) throws ApplicationException {
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
	public void delete(HistoryDTO dto) throws ApplicationException {
		Session session=null;
		Transaction tx=null;
		
		try {
			session=HibDataSource.getSession();
			tx = session.beginTransaction();
		
			session.delete(dto);
		
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
		
	}

	@Override
	public void update(HistoryDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session=null;
		Transaction tx=null;
		
		try {
			session=HibDataSource.getSession();
			tx = session.beginTransaction();
		
			session.update(dto);
		
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
		
		
	}

	@Override
	public List list() throws ApplicationException {
		
		return list(0,0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session=null;
		List list=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(HistoryDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
				
				
			}
			 list=criteria.list();
			
		}
		catch (HibernateException e) {

			
			e.printStackTrace();
			throw new ApplicationException("Exception in Catering add " + e.getMessage());

		} finally {
			session.close();
		}
		
		return list;
	}

	@Override
	public List search(HistoryDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto,0,0);
	}

	@Override
	public List<HistoryDTO> search(HistoryDTO dto, int pageNo, int pageSize)
	        throws ApplicationException {

	    Session session = null;
	    List<HistoryDTO> list = null;

	    try {
	        session = HibDataSource.getSession();

	        Criteria criteria = session.createCriteria(HistoryDTO.class);

	        if (dto != null) {

	            if (dto.getId() !=null && dto.getId() > 0) {
	                criteria.add(Restrictions.eq("id", dto.getId()));
	            }

	            if (dto.getCode()!= null && dto.getCode().length() > 0) {
	                criteria.add(Restrictions.like("code", dto.getCode() + "%")); // ✅ case-insensitive
	            }
                 
	            if (dto.getName() != null && dto.getName().length() > 0) {
	                criteria.add(Restrictions.like("name", dto.getName() + "%")); // ✅ case-insensitive
	            }
	           
	            if (dto.getLoginTime() != null && dto.getLoginTime().getTime() > 0) {
					criteria.add(Restrictions.eq("loginTime", dto.getLoginTime()));
				}
	            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
	                criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
	            }
	        }

	        // ✅ Pagination
	        if (pageSize > 0) {
	            int startIndex = (pageNo - 1) * pageSize;
	            criteria.setFirstResult(startIndex);
	            criteria.setMaxResults(pageSize);
	        }

	        list = criteria.list();

	    } catch (HibernateException e) {

	        e.printStackTrace();
	        throw new ApplicationException("Exception in search " + e.getMessage());

	    } finally {
	        if (session != null) {
	            session.close(); // ✅ safe close
	        }
	    }

	    return list;
	}

	@Override
	public HistoryDTO findByPK(long pk) throws ApplicationException {
		Session session=null;
		HistoryDTO dto=null;
		try {
			session=HibDataSource.getSession();
			dto=(HistoryDTO) session.get(HistoryDTO.class, pk);
			
		}
		catch (HibernateException e) {

		
			e.printStackTrace();
			throw new ApplicationException("Exception in Historrrrrrrry add " + e.getMessage());

		} finally {
			session.close();
		}
		
		return dto;
		
	}

	@Override
	public HistoryDTO findByCode(String code) throws ApplicationException {
		Session session=null;
		HistoryDTO dto=null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HistoryDTO.class);
			criteria.add(Restrictions.eq("code",code));
			List list=criteria.list();
			if(list.size()>0) {
			dto=	(HistoryDTO) list.get(0);
			}
		}
			
			catch (HibernateException e) {

				
				e.printStackTrace();
				throw new ApplicationException("Exception in Catering add " + e.getMessage());

			} finally {
				session.close();
			}
			
			return dto;
			
		}
}
