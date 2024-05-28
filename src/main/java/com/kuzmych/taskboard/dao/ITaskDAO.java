package com.kuzmych.taskboard.dao;

import java.util.List;

import com.kuzmych.taskboard.entity.Task;

public interface ITaskDAO {

	Task findById(Long id);

	List<Task> findAll();

	void save(Task task);

	void deleteById(Long id);

}
