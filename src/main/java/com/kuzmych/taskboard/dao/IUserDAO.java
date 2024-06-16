package com.kuzmych.taskboard.dao;

import java.util.List;

import com.kuzmych.taskboard.entity.User;

public interface IUserDAO {

	User findById(Long id);

	List<User> findAll();

	void save(User user);

	void deleteById(Long id);

	void update(User user);

}