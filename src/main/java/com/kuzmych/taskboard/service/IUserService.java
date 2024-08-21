package com.kuzmych.taskboard.service;

import com.kuzmych.taskboard.entity.User;



public interface IUserService {

	User findById(Long id);

	void save(User user);
	
	void update(User user);

	void delete(Long id);
	
	boolean authenticateUser(String username, String plainPassword);
	

}
