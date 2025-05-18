package com.kuzmych.taskboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kuzmych.taskboard.entity.User;

@Repository
public class UserDAO implements IUserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findById(Long id) {
		return sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public List<User> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
	}

	@Override
	public void save(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@Override
	public void deleteById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, id);
		if (user != null) {
			session.delete(user);
		}
	}

	@Override
	public void update(User user) {
		sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public User findByUserName(String userName) {
		String hql = "FROM User WHERE login = :userName";
		return (User) sessionFactory.getCurrentSession().createQuery(hql).setParameter("userName", userName)
				.uniqueResult();
	}

	@Override
	public User findByUserEmail(String userEmail) {
		return sessionFactory.getCurrentSession().createQuery("FROM User WHERE email = :email", User.class)
				.setParameter("email", userEmail).uniqueResult();
	}
	
	@Override
	public boolean existsByLogin(String login) {
	    return sessionFactory.getCurrentSession()
	        .createQuery("FROM User WHERE login = :login", User.class)
	        .setParameter("login", login)
	        .uniqueResult() != null;
	}

	@Override
	public boolean existsByEmail(String email) {
	    return sessionFactory.getCurrentSession()
	        .createQuery("FROM User WHERE email = :email", User.class)
	        .setParameter("email", email)
	        .uniqueResult() != null;
	}

	@Override
	public User findByUserResetToken(String token) {

		return sessionFactory.getCurrentSession().createQuery("FROM User WHERE resetToken = :token", User.class)
				.setParameter("token", token).uniqueResult();
	}

}
