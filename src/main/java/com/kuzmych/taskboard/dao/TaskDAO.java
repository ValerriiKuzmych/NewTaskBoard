package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.config.HibernateUtil;
import com.kuzmych.taskboard.entity.Task;

@Repository
public class TaskDAO implements ITaskDAO {

	@Override
	public Task findById(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Task task = session.get(Task.class, id);

		session.close();

		return task;
	}

	@Override
	public List<Task> findAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Task> taskBoard = session.createQuery("from Task", Task.class).list();

		session.close();

		return taskBoard;
	}

	@Override
	public void save(Task task) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.saveOrUpdate(task);

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

			Task task = session.get(Task.class, id);

			if (task != null) {

				session.delete(task);
			}

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null)

				transaction.rollback();

			e.printStackTrace();
		}
	}

}
