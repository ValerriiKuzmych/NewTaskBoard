package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.Task;

@Repository
public class TaskDAO implements ITaskDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Task findById(Long id) {
		return sessionFactory.getCurrentSession().get(Task.class, id);
	}

	@Override
	public List<Task> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Task", Task.class).list();
	}

	@Override
	public void save(Task task) {
		System.out.println(
				"Saving Task in DAO: " + task.getName() + ", Description: " + task.getDescription());
		
		sessionFactory.getCurrentSession().saveOrUpdate(task);
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Task task = session.get(Task.class, id);
		if (task != null) {
			session.delete(task);
		}
	}

	@Override
	public void update(Task task) {
		sessionFactory.getCurrentSession().update(task);
	}
}



