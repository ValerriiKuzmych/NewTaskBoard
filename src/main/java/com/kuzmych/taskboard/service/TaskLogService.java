package com.kuzmych.taskboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.ITaskLogDAO;
import com.kuzmych.taskboard.entity.TaskLog;

@Service
public class TaskLogService implements ITaskLogService {

	@Autowired
	private ITaskLogDAO taskLogDAO;

	@Transactional(readOnly = true)
	@Override
	public List<TaskLog> getAllLogs(Long id) {
		return taskLogDAO.getAllLogs(id);
	}

	@Transactional
	@Override
	public void saveLog(TaskLog taskLog) {
		
		taskLogDAO.saveLog(taskLog);
	}

	
}
