package com.kuzmych.taskboard.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.ITaskBoardDAO;
import com.kuzmych.taskboard.dao.ITaskDAO;
import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.entity.TaskBoard;

@Service
public class TaskBoardService implements ITaskBoardService {

	@Autowired
	private ITaskBoardDAO taskBoardDAO;
	@Autowired
	private ITaskDAO taskDAO;

	@Transactional(readOnly = true)
	@Override
	public TaskBoard findById(Long id) {
		TaskBoard taskBoard = taskBoardDAO.findById(id);
		if (taskBoard != null) {

			taskBoard.getTasks().size();
			taskBoard.getUsersWithAccess().size();
		}
		return taskBoard;
	}

	@Transactional
	@Override
	public List<TaskBoard> findAll() {
		return taskBoardDAO.findAll();
	}

	@Transactional
	@Override
	public void save(TaskBoard taskBoard) {
		taskBoardDAO.save(taskBoard);
	}

	@Transactional
	@Override
	public void addTaskToTaskBoard(Long taskBoardId, Task task) {
		TaskBoard taskBoard = taskBoardDAO.findById(taskBoardId);
		task.setTaskBoard(taskBoard);
		task.setCreatedDate(LocalDateTime.now());
		taskDAO.save(task);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		TaskBoard taskBoard = taskBoardDAO.findById(id);
		if (taskBoard != null) {
			try {

				taskBoardDAO.deleteById(id);

			} catch (OptimisticLockingFailureException ex) {

				throw new RuntimeException("Failed to delete task board due to optimistic locking conflict");
			}
		} else {
			throw new EntityNotFoundException("TaskBoard not found with ID: " + id);
		}
	}

	@Transactional
	@Override
	public void update(TaskBoard taskBoard) {

		TaskBoard existingTaskBoard = findById(taskBoard.getId());

		if (existingTaskBoard != null) {
			existingTaskBoard.setName(taskBoard.getName());
			existingTaskBoard.setDescription(taskBoard.getDescription());
			existingTaskBoard.setUsersWithAccess(taskBoard.getUsersWithAccess());
			if (taskBoard.getVersion() == null) {
				taskBoard.setVersion(0L); // Default version
			}

			existingTaskBoard.setVersion(taskBoard.getVersion());

			taskBoardDAO.update(existingTaskBoard);
			System.out.println("Updated TaskBoard: " + existingTaskBoard);
		} else {
			throw new EntityNotFoundException("TaskBoard not found");
		}
	}

	@Override
	public List<Task> getTasksForBoard(Long taskBoardId) {
		List<Task> tasks = taskBoardDAO.getTasksForBoard(taskBoardId);
		return tasks;
	}
}
