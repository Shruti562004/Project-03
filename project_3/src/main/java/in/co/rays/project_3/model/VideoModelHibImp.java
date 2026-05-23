package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.HistoryDTO;
import in.co.rays.project_3.dto.VideoDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Video model
 * 
 * @author Shruti Rathore
 *
 */
public  class VideoModelHibImp implements VideoModelInt {

	public long add(VideoDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		long pk = 0;
		VideoDTO existDto = findByCategory(dto.getCategory());
		if (existDto != null) {
			throw new DuplicateRecordException("Video already exist");
		}
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Video Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(VideoDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Video delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(VideoDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Video update " + e.getMessage());
		} finally {
			session.close();
		}

	}

	public VideoDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println("======" + pk);
		Session session = null;
		VideoDTO dto = null;
		try {
			session = HibDataSource.getSession();

			dto = (VideoDTO) session.get(VideoDTO.class, pk);
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting Video by pk");
		} finally {
			session.close();
		}
		System.out.println("-------" + dto);
		return dto;
	}

	public VideoDTO findByCategory(String category) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		VideoDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(VideoDTO.class);
			criteria.add(Restrictions.eq("category", category));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (VideoDTO) list.get(0);
			}
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());

		} finally {
			session.close();
		}
		return dto;
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(VideoDTO.class);
			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (JDBCConnectionException e) {
			throw e;
		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in  Video list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(VideoDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(VideoDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(VideoDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getTitle() != null && dto.getTitle().length() > 0) {
				criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
			}
			if (dto.getCategory() != null && dto.getCategory().length() > 0) {
				criteria.add(Restrictions.like("category", dto.getCategory() + "%"));
			}
			if (dto.getDuration() > 0) {
				criteria.add(Restrictions.eq("duration", dto.getDuration()));
			}
			if (dto.getViews() > 0) {
				criteria.add(Restrictions.eq("views", dto.getViews()));
			}

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Video search");
		} finally {
			session.close();
		}

		return list;
	}

	

}
