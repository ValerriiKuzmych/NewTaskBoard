package com.kuzmych.taskboard.dao;

import java.util.List;

import com.kuzmych.taskboard.entity.TaskBoard;

public interface ITaskBoardDAO {

	TaskBoard findById(Long id);

	List<TaskBoard> findAll();

	void save(TaskBoard user);

	void deleteById(Long id);

}