package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.TaskBoard;

@Repository
public class TaskBoardDAO implements ITaskBoardDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public TaskBoard findById(Long id) {
		return sessionFactory.getCurrentSession().get(TaskBoard.class, id);
	}

	@Override
	public List<TaskBoard> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from TaskBoard", TaskBoard.class).list();
	}

	@Override
	public void save(TaskBoard taskBoard) {

		System.out.println(
				"Saving TaskBoard in DAO: " + taskBoard.getName() + ", Description: " + taskBoard.getDescription());

		sessionFactory.getCurrentSession().saveOrUpdate(taskBoard);
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		TaskBoard taskBoard = session.get(TaskBoard.class, id);
		if (taskBoard != null) {
			session.delete(taskBoard);
		}
	}

	@Override
	public void update(TaskBoard taskBoard) {
		sessionFactory.getCurrentSession().update(taskBoard);
	}
}
