package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.Task;

public interface ITaskService {

	Task findById(Long id);

	List<Task> findAll();

	void save(Task task);

	void delete(Long id);

	void update(Task Task);

	/* void updateTaskStatus(Long taskId, TaskStatus newStatus); */

}
