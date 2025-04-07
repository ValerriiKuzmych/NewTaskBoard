package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.TaskLog;

@Repository
public class TaskLogDAO implements ITaskLogDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void saveLog(TaskLog taskLog) {
		sessionFactory.getCurrentSession().save(taskLog);

	}

	@Override
	public List<TaskLog> getAllLogs(Long taskId) {

		return sessionFactory.getCurrentSession().createQuery("FROM TaskLog", TaskLog.class).list();

	}

	@Override
	public List<TaskLog> getLogsByTaskId(Long taskId) {
		
		return sessionFactory.getCurrentSession().createQuery("FROM TaskLog WHERE task.id = :taskId", TaskLog.class)
				.setParameter("taskId", taskId).list();
	}

}
