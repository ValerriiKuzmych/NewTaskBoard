package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.TaskBoardLog;

@Repository
public class TaskBoardLogDAO implements ITaskBoardLogDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<TaskBoardLog> getAllLogs(Long id) {

		return sessionFactory.getCurrentSession().createQuery("FROM TaskBoardLog", TaskBoardLog.class).list();
	}

	@Override
	public void save(TaskBoardLog taskBoardLog) {

		sessionFactory.getCurrentSession().save(taskBoardLog);

	}

}
