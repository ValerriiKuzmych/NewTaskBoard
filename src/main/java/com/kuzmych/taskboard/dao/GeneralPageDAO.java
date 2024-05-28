package com.kuzmych.taskboard.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.util.HibernateUtil;

@Repository
public class GeneralPageDAO implements IGeneralPageDAO {

	@Override
	public GeneralPage findById(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		GeneralPage generalPage = session.get(GeneralPage.class, id);

		session.close();

		return generalPage;
	}

	@Override
	public void save(GeneralPage generalPage) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.saveOrUpdate(generalPage);

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null)
				
				transaction.rollback();

			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Long id) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			GeneralPage generalPage = session.get(GeneralPage.class, id);

			if (generalPage != null) {

				session.delete(generalPage);
			}
			transaction.commit();

		} catch (Exception e) {

			if (transaction != null)

				transaction.rollback();

			e.printStackTrace();
		}

	}

}
