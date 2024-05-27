package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.TaskBoard;


public interface ITaskBoardService {
	
	TaskBoard findById(Long id);

	List<TaskBoard> findAll();

	void save(TaskBoard user);

	void delete(Long id);

}
