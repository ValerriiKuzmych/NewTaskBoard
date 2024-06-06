package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.config.HibernateUtil;
import com.kuzmych.taskboard.entity.User;


@Repository
public class UserDAO implements IUserDAO {

	@Override
	public User findById(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		User user = session.get(User.class, id);

		session.close();

		return user;
	}

	@Override
	public List<User> findAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<User> users = session.createQuery("from User", User.class).list();

		session.close();

		return users;
	}

	@Override
	public void save(User user) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.saveOrUpdate(user);

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

			User user = session.get(User.class, id);

			if (user != null) {

				session.delete(user);
			}

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null)

				transaction.rollback();

			e.printStackTrace();
		}
	}

}
