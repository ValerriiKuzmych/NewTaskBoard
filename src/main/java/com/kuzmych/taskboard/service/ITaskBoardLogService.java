package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.TaskBoardLog;

public interface ITaskBoardLogService {

	List<TaskBoardLog> getAllLogs(Long id);

	void saveTaskLogInTaskBoard(Long taskId);

}
