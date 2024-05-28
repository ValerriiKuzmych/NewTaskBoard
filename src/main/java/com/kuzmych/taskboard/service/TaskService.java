package com.kuzmych.taskboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuzmych.taskboard.dao.TaskDAO;
import com.kuzmych.taskboard.entity.Task;

@Service
public class TaskService implements ITaskService {

	@Autowired
	private TaskDAO taskDAO;

	@Override
	public Task findById(Long id) {
		return taskDAO.findById(id);
	}

	@Override
	public List<Task> findAll() {
		return taskDAO.findAll();
	}

	@Override
	public void save(Task task) {
		taskDAO.save(task);
	}

	@Override
	public void delete(Long id) {
		taskDAO.deleteById(id);
	}

}
