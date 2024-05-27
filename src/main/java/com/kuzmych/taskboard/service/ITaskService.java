package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.Task;


public interface ITaskService {
	
	Task findById(Long id);

	List<Task> findAll();

	void save(Task user);

	void delete(Long id);

}
