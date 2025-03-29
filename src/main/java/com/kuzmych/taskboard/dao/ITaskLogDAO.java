package com.kuzmych.taskboard.dao;

import java.util.List;

import com.kuzmych.taskboard.entity.TaskLog;

public interface ITaskLogDAO {

	List<TaskLog> getAllLogs(Long id);

	void saveLog(TaskLog taskLog);

}
