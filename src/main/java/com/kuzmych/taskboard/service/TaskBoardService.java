package com.kuzmych.taskboard.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.ITaskBoardDAO;
import com.kuzmych.taskboard.entity.TaskBoard;

@Service
@Transactional
public class TaskBoardService implements ITaskBoardService {

	@Autowired
	private ITaskBoardDAO taskBoardDAO;

	@Override
	public TaskBoard findById(Long id) {
		return taskBoardDAO.findById(id);
	}

	@Override
	public List<TaskBoard> findAll() {
		return taskBoardDAO.findAll();
	}

	@Override
	public void save(TaskBoard taskBoard) {
		taskBoardDAO.save(taskBoard);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		TaskBoard taskBoard = taskBoardDAO.findById(id);
		if (taskBoard != null) {
			try {

				taskBoardDAO.deleteById(id);
				System.out.println("Deleted TaskBoard with ID: " + id + " and Version: " + taskBoard.getVersion());
			} catch (OptimisticLockingFailureException ex) {
				System.err.println("Failed to delete task board due to optimistic locking conflict. ID: " + id
						+ " Version: " + taskBoard.getVersion());
				throw new RuntimeException("Failed to delete task board due to optimistic locking conflict");
			}
		} else {
			throw new EntityNotFoundException("TaskBoard not found with ID: " + id);
		}
	}

	@Override
	public void update(TaskBoard taskBoard) {
		TaskBoard existingTaskBoard = taskBoardDAO.findById(taskBoard.getId());
		if (existingTaskBoard != null) {
			existingTaskBoard.setName(taskBoard.getName());
			existingTaskBoard.setDescription(taskBoard.getDescription());

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
}
