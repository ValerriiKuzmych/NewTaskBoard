package com.kuzmych.taskboard.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.kuzmych.taskboard.dao.ITaskDAO;
import com.kuzmych.taskboard.entity.Task;

@Service
public class TaskService implements ITaskService {

	@Autowired
	private ITaskDAO taskDAO;

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
		Task task = taskDAO.findById(id);
		if (task != null) {
			try {

				taskDAO.deleteById(id);
				System.out.println("Deleted Task with ID: " + id + " and Version: " + task.getVersion());
			} catch (OptimisticLockingFailureException ex) {
				System.err.println("Failed to delete task due to optimistic locking conflict. ID: " + id + " Version: "
						+ task.getVersion());
				throw new RuntimeException("Failed to delete task  due to optimistic locking conflict");
			}
		} else {
			throw new EntityNotFoundException("Task not found with ID: " + id);
		}
	}

	@Override
	public void update(Task task) {
		Task existingTask = taskDAO.findById(task.getId());
		if (existingTask != null) {
			existingTask.setName(task.getName());
			existingTask.setDescription(task.getDescription());

			if (task.getVersion() == null) {
				task.setVersion(0L); // Default version
			}

			existingTask.setVersion(task.getVersion());

			taskDAO.update(existingTask);
			System.out.println("Updated Task: " + existingTask);
		} else {
			throw new EntityNotFoundException("Task not found");
		}
	}

}
