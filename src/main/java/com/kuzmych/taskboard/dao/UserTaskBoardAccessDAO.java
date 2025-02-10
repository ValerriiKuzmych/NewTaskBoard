package com.kuzmych.taskboard.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.UserTaskBoardAccess;

@Repository
public class UserTaskBoardAccessDAO implements IUserTaskBoardAccessDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveAccess(UserTaskBoardAccess access) {
		sessionFactory.getCurrentSession().saveOrUpdate(access);

	}

	@Override
	public void deleteAccess(UserTaskBoardAccess access) {
		sessionFactory.getCurrentSession().delete(access);

	}
	
	@Override
	public void updateAccess(UserTaskBoardAccess access) {
		sessionFactory.getCurrentSession().update(access);

	}

}
