package com.kuzmych.taskboard.service;

import com.kuzmych.taskboard.dto.UserRegistrationDTO;
import com.kuzmych.taskboard.entity.User;

public interface IUserService {

	User findById(Long id);

	void save(User user);

	void update(User user);

	void delete(Long id);

	void updatePassword(User user);

	boolean authenticateUser(String username, String plainPassword);
	
	boolean isLoginOrEmailTaken(String login, String email);

	User convertingToUser(UserRegistrationDTO userDTO);

	User findByUserName(String userName);

	User findByNameOrId(String userIdentifier);

	User findByUserEmail(String userEmail);

	User findByUserResetToken(String token);

	String getCurrentUserLogin();
}
