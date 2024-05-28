package com.kuzmych.taskboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kuzmych.taskboard.dao.TaskBoardDAO;
import com.kuzmych.taskboard.entity.TaskBoard;

@Service
public class TaskBoardService implements ITaskBoardService {

	@Autowired
	private TaskBoardDAO taskBoardDAO;

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

}
