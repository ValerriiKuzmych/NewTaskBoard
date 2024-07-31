package com.kuzmych.taskboard.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.GeneralPage;

@Repository
public class GeneralPageDAO implements IGeneralPageDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public GeneralPage findById(Long id) {
		
	    return sessionFactory.getCurrentSession().get(GeneralPage.class, id);
	}

	@Override
	public void save(GeneralPage generalPage) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(generalPage);
	}


	@Override
	public void update(GeneralPage generalPage) {
		sessionFactory.getCurrentSession().update(generalPage);
	}
}
