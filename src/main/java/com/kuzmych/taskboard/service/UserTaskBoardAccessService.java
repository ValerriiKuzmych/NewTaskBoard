package com.kuzmych.taskboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.IUserTaskBoardAccessDAO;
import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.entity.UserTaskBoardAccess;
import com.kuzmych.taskboard.entity.User;

@Service
public class UserTaskBoardAccessService implements IUserTaskBoardAccessService {

	@Autowired
	IUserTaskBoardAccessDAO userTaskBoardAccessDAO;
	@Autowired
	IUserService userService;
	@Autowired
	ITaskBoardService taskBoardService;

	@Transactional
	@Override
	public void giveAccessToTaskBoard(Long taskBoardId, String userIdentifier, UserTaskBoardAccess access) {

		User user = userService.findByNameOrId(userIdentifier);
		TaskBoard taskBoard = taskBoardService.findById(taskBoardId);

//		List<UserTaskBoardAccess> existingUserTaskBoardAccesses = user.getTaskBoardAccesses();
//
//		if (existingUserTaskBoardAccesses == null) {
//			existingUserTaskBoardAccesses = new ArrayList<>();
//		}
//
//		for (UserTaskBoardAccess existingUserTaskBoardAccess : existingUserTaskBoardAccesses) {
//
//			if (existingUserTaskBoardAccess.getTaskBoard().getId() == (taskBoardId)) {
//
//				existingUserTaskBoardAccess.setCreatingNewTask(access.isCreatingNewTask());
//				existingUserTaskBoardAccess.setDeletingTask(access.isDeletingTask());
//				existingUserTaskBoardAccess.setEditingTask(access.isEditingTask());
//				existingUserTaskBoardAccess.setReadingTask(access.isReadingTask());
//
//				userTaskBoardAccessDAO.updateAccess(existingUserTaskBoardAccess);
//
//				return;
//
//			}

		access.setUser(user);
		access.setTaskBoard(taskBoard);

		userTaskBoardAccessDAO.saveAccess(access);

	}

	@Transactional
	@Override
	public void deleteAccessToTaskBoard(Long taskBoardId, Long userId) {

//		userTaskBoardAccessDAO.deleteAccess(access);

	}

	@Override
	public boolean chekAccessToTaskBoard(Long loggedInUserId, Long taskBoardId) {

		User user = userService.findById(loggedInUserId);
		boolean hasAccess = false;

		for (UserTaskBoardAccess access : user.getTaskBoardAccesses()) {

			if (access.getTaskBoard().getId() == taskBoardId) {

				hasAccess = true;

				break;
			}
		}

		return hasAccess;
	}

	@Override
	public boolean chekAccessToCreatingNewTask(Long loggedInUserId, Long taskBoardId) {

		User user = userService.findById(loggedInUserId);
		boolean hasAccess = false;

		for (UserTaskBoardAccess access : user.getTaskBoardAccesses()) {

			if (access.getTaskBoard().getId() == taskBoardId && access.isCreatingNewTask() == true) {

				hasAccess = true;

				break;
			}
		}

		return hasAccess;
	}

	@Override
	public boolean chekAccessToReadingTask(Long loggedInUserId, Long taskBoardId) {

		User user = userService.findById(loggedInUserId);
		boolean hasAccess = false;

		for (UserTaskBoardAccess access : user.getTaskBoardAccesses()) {

			if (access.getTaskBoard().getId() == taskBoardId && access.isReadingTask() == true) {

				hasAccess = true;

				break;
			}
		}

		return hasAccess;
	}

	@Override
	public boolean chekAccessToDeletingTask(Long loggedInUserId, Long taskBoardId) {

		User user = userService.findById(loggedInUserId);
		boolean hasAccess = false;

		for (UserTaskBoardAccess access : user.getTaskBoardAccesses()) {

			if (access.getTaskBoard().getId() == taskBoardId && access.isDeletingTask() == true) {

				hasAccess = true;

				break;
			}
		}

		return hasAccess;

	}

	@Override
	public boolean chekAccessToEditingTask(Long loggedInUserId, Long taskBoardId) {

		User user = userService.findById(loggedInUserId);
		boolean hasAccess = false;

		for (UserTaskBoardAccess access : user.getTaskBoardAccesses()) {

			if (access.getTaskBoard().getId() == taskBoardId && access.isEditingTask() == true) {

				hasAccess = true;

				break;
			}
		}

		return hasAccess;

	}
}
