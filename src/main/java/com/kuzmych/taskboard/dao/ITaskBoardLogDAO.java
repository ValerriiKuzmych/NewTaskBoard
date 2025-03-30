package com.kuzmych.taskboard.dao;

import java.util.List;

import com.kuzmych.taskboard.entity.TaskBoardLog;

public interface ITaskBoardLogDAO {

	List<TaskBoardLog> getAllLogs(Long id);

	void save(TaskBoardLog taskLog);

}
