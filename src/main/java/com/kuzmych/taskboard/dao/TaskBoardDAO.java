package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.config.HibernateUtil;
import com.kuzmych.taskboard.entity.TaskBoard;


@Repository
public class TaskBoardDAO implements ITaskBoardDAO {

	@Override
	public TaskBoard findById(Long id) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		TaskBoard taskBoard = session.get(TaskBoard.class, id);

		session.close();

		return taskBoard;
	}

	@Override
	public List<TaskBoard> findAll() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<TaskBoard> taskBoard = session.createQuery("from TaskBoard", TaskBoard.class).list();

		session.close();

		return taskBoard;
	}

	@Override
	public void save(TaskBoard taskBoard) {

		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();

			session.saveOrUpdate(taskBoard);

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
			
			TaskBoard taskBoard = session.get(TaskBoard.class, id);
			
			if (taskBoard != null) {
				
				session.delete(taskBoard);
			}
			
			transaction.commit();
			
		} catch (Exception e) {
			
			if (transaction != null)
				
				transaction.rollback();
			
			e.printStackTrace();
		}
	}

}
