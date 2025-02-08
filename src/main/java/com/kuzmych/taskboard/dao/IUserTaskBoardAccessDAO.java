package com.kuzmych.taskboard.dao;

import com.kuzmych.taskboard.entity.UserTaskBoardAccess;

public interface IUserTaskBoardAccessDAO {
	
	void saveAccess(UserTaskBoardAccess access);
	void deleteAccess(UserTaskBoardAccess access);

}
