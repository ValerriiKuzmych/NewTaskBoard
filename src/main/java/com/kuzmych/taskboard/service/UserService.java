package com.kuzmych.taskboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuzmych.taskboard.dao.UserDAO;
import com.kuzmych.taskboard.entity.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public User findById(Long id) {
		return userDAO.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	public void save(User user) {
		userDAO.save(user);
	}

	@Override
	public void delete(Long id) {
		userDAO.deleteById(id);
	}

}
