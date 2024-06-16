package com.kuzmych.taskboard.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void delete(Long id) {
		taskBoardDAO.deleteById(id);
	}

	@Override
	public void update(TaskBoard taskBoard) {
		TaskBoard existingTaskBoard = taskBoardDAO.findById(taskBoard.getId());
		if (existingTaskBoard != null) {
			existingTaskBoard.setName(taskBoard.getName());
			existingTaskBoard.setDescription(taskBoard.getDescription());

			// Ensure version is properly set
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
