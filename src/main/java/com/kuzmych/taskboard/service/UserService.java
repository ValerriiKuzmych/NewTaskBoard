package com.kuzmych.taskboard.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.IGeneralPageDAO;
import com.kuzmych.taskboard.dao.IUserDAO;
import com.kuzmych.taskboard.dto.UserRegistrationDTO;
import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.entity.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private IGeneralPageDAO generalPageDAO;

	@Transactional(readOnly = true)
	@Override
	public User findById(Long id) {

		User user;

		user = userDAO.findById(id);

		if (user != null) {

			user.getTaskBoardAccesses().size();

		}

		return user;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByUserName(String userName) {

		User user;

		user = userDAO.findByUserName(userName);

		return user;
	}

	@Transactional(readOnly = true)
	@Override
	public User findByNameOrId(String userIdentifier) {

		User user;

		try {
			user = userDAO.findByUserName(userIdentifier);

		} catch (NumberFormatException e) {
			Long userId = Long.parseLong(userIdentifier);
			user = userDAO.findById(userId);
		}

		return user;

	}

	@Transactional
	@Override
	public void save(User user) {

		user.setPassword(passwordService.hashPassword(user.getPassword()));

		User userWithGeneralPage = createGeneralPageForNewUser(user);

		userDAO.save(userWithGeneralPage);
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
			existingUser.setTaskBoardAccesses(user.getTaskBoardAccesses());
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

	@Transactional
	public boolean authenticateUser(String username, String plainPassword) {

		User user = userDAO.findByUserName(username);

		if (user != null) {

			return passwordService.checkPassword(plainPassword, user.getPassword());
		}
		return false;
	}

	public User createGeneralPageForNewUser(User user) {

		GeneralPage generalPage = new GeneralPage();

		generalPage.setUser(user);

		generalPageDAO.save(generalPage);

		user.setGeneralPage(generalPage);

		return user;

	}

	@Override
	public User convertingToUser(UserRegistrationDTO userDTO) {

		User user = new User();

		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		user.setLogin(userDTO.getLogin());
		user.setPassword(userDTO.getPassword());

		return user;

	}

	public boolean checkUserAccess(User loggedInUser) {
		loggedInUser.getLogin();

		return true;
	};

}
