package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PlacementDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class PlacementModelHibImp implements PlacementModelInt {

	public long add(PlacementDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		PlacementModelInt Placementmod = ModelFactory.getInstance().getPlacementModel();
		PlacementDTO Placementdto = Placementmod.findByName(dto.getName());
		if (Placementdto != null) {
			throw new DuplicateRecordException("name already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
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
	public void delete(PlacementDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
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
	public void update(PlacementDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;
		PlacementModelInt Placementmod = ModelFactory.getInstance().getPlacementModel();
		PlacementDTO Placementdto = findByName(dto.getName());
		if (Placementdto != null && Placementdto.getId() != dto.getId()) {
			throw new DuplicateRecordException("name  already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
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
		Session session = null;
		List list = null;
		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PlacementDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();
		} catch (HibernateException e) {

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
	public List search(PlacementDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	@Override
	public List search(PlacementDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PlacementDTO.class);
			if (dto != null) {
				if (dto.getCoordinates() != null && dto.getCoordinates().length() > 0) {

					criteria.add(Restrictions.like("coordinates", dto.getCoordinates() + "%"));
				}

				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));

				}

				if (dto.getScale() != null && dto.getScale().length() > 0) {
					criteria.add(Restrictions.like("scale", dto.getScale() + "%"));

				}

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}

			list = criteria.list();

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
	public PlacementDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		PlacementDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (PlacementDTO) session.get(PlacementDTO.class, pk);

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
	public PlacementDTO findByName(String name) throws ApplicationException {

		Session session = null;
		PlacementDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PlacementDTO.class);

			criteria.add(Restrictions.eq("name", name));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (PlacementDTO) list.get(0);
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
