package com.kuzmych.taskboard.service;

import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.entity.TaskBoard;

public interface IGeneralPageService {

	GeneralPage findById(Long id);

	void save(GeneralPage generalPage);

	void addTaskBoardToGeneralPage(Long generalPageId, TaskBoard taskBoard);

}
