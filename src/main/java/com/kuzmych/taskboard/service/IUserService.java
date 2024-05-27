package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.User;



public interface IUserService {

	User findById(Long id);

	List<User> findAll();

	void save(User user);

	void delete(Long id);

}
