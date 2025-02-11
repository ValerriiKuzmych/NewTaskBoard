package com.kuzmych.taskboard.service;

import com.kuzmych.taskboard.entity.UserTaskBoardAccess;

public interface IUserTaskBoardAccessService {

	void giveAccessToTaskBoard(Long taskBoardId, String userIdentifier, UserTaskBoardAccess access);

	void deleteAccessToTaskBoard(Long taskBoardId, String userIdentifier);

	boolean chekAccessToTaskBoard(Long loggedInUserId, Long taskBoardId);

	boolean chekAccessToCreatingNewTask(Long loggedInUserId, Long taskBoardId);

	boolean chekAccessToReadingTask(Long loggedInUserId, Long taskBoardId);

	boolean chekAccessToDeletingTask(Long loggedInUserId, Long taskBoardId);

	boolean chekAccessToEditingTask(Long loggedInUserId, Long taskBoardId);
}
