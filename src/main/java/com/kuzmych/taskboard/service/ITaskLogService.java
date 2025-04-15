package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.TaskLog;

public interface ITaskLogService {

	List<TaskLog> getAllLogs(Long id);

	List<TaskLog> getLogsByTaskId(Long taskId);

	void save(TaskLog taskLog);

}
