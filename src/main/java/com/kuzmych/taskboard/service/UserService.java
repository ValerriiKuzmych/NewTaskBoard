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

		return userDAO.findById(id);
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

}
