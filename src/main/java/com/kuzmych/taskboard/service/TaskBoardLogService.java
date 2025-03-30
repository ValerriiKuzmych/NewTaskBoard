package com.kuzmych.taskboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kuzmych.taskboard.dao.ITaskBoardLogDAO;
import com.kuzmych.taskboard.dao.ITaskLogDAO;
import com.kuzmych.taskboard.entity.TaskBoardLog;
import com.kuzmych.taskboard.entity.TaskLog;

@Service
public class TaskBoardLogService implements ITaskBoardLogService {

	@Autowired
	private ITaskBoardLogDAO taskBoardLogDAO;

	@Autowired
	private ITaskLogDAO taskLogDAO;

	@Transactional(readOnly = true)
	@Override
	public List<TaskBoardLog> getAllLogs(Long id) {

		return taskBoardLogDAO.getAllLogs(id);
	}

	@Transactional
	@Override
	public void saveTaskLogInTaskBoard(Long taskId) {

		TaskBoardLog logEntry = new TaskBoardLog();

		List<TaskLog> logs = taskLogDAO.getLogsByTaskId(taskId);

		if (logs != null) {

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				String logsJson = objectMapper.writeValueAsString(logs);

				logEntry.setAction("TASK_DELETED");
				logEntry.setId(taskId);
				// TODO
				logEntry.setChangedBy("Unknown");
				logEntry.setDetails(logsJson);

				taskBoardLogDAO.save(logEntry);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
