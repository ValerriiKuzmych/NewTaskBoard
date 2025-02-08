package com.kuzmych.taskboard.service;

import com.kuzmych.taskboard.entity.UserTaskBoardAccess;

public interface IUserTaskBoardAccessService {

	void giveAccessToTaskBoard(Long taskBoardId, String userIdentifier, UserTaskBoardAccess access);

	void deleteAccessToTaskBoard(Long taskBoardId, Long userId);
}
