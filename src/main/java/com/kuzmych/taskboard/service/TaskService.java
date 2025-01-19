package com.kuzmych.taskboard.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.ITaskDAO;
import com.kuzmych.taskboard.entity.Task;

@Transactional
@Service
public class TaskService implements ITaskService {

	@Autowired
	private ITaskDAO taskDAO;

	@Transactional(readOnly = true)
	@Override
	public Task findById(Long id) {

		return taskDAO.findById(id);
	}

	@Transactional
	@Override
	public List<Task> findAll() {
		return taskDAO.findAll();
	}

	@Transactional
	@Override
	public void save(Task task) {

		task.setCreatedDate(LocalDateTime.now());
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

	@Transactional
	@Override
	public void update(Task task) {

		System.out.println("Updating Task ID: " + task.getId());

		Task existingTask = taskDAO.findById(task.getId());

		if (existingTask != null) {
			existingTask.setName(task.getName());
			existingTask.setDescription(task.getDescription());
			existingTask.setVersion(task.getVersion());
			existingTask.setFilePath(task.getFilePath());
			existingTask.setPriority(task.getPriority());
			existingTask.setTaskStatus(task.getTaskStatus());
			existingTask.setExecutorName(task.getExecutorName());
			taskDAO.update(existingTask);
		} else {
			throw new EntityNotFoundException("Task not found with ID: " + task.getId());
		}
	}

}
