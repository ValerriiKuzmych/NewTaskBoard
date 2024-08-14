package com.kuzmych.taskboard.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.UserDAO;
import com.kuzmych.taskboard.entity.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserDAO userDAO;

	@Transactional(readOnly = true)
	@Override
	public User findById(Long id) {

		return userDAO.findById(id);
	}

	@Transactional
	@Override
	public void save(User user) {

		userDAO.save(user);
	}

	@Override
	public void delete(Long id) {
		userDAO.deleteById(id);
	}

	@Override
	public void update(User user) {
		User existingUser = findById(user.getId());

		if (existingUser != null) {
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setLogin(user.getLogin());

			if (user.getVersion() == null) {
				user.setVersion(0L); // Default version
			}

			existingUser.setVersion(user.getVersion());

			userDAO.update(existingUser);
			System.out.println("Updated User: " + existingUser);
		} else {
			throw new EntityNotFoundException("User not found");
		}
	}

}
