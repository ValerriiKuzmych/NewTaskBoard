package com.kuzmych.taskboard.service;

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
		access.setUser(user);
		access.setTaskBoard(taskBoard);

		userTaskBoardAccessDAO.saveAccess(access);

	}

	@Transactional
	@Override
	public void deleteAccessToTaskBoard(Long taskBoardId, Long userId) {

//		userTaskBoardAccessDAO.deleteAccess(access);

	}

}
