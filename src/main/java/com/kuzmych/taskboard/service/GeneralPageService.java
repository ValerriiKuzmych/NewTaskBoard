package com.kuzmych.taskboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.IGeneralPageDAO;
import com.kuzmych.taskboard.dao.ITaskBoardDAO;
import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.entity.TaskBoard;

@Service
public class GeneralPageService implements IGeneralPageService {

	@Autowired
	private IGeneralPageDAO generalPageDAO;

	@Autowired
	private ITaskBoardDAO taskBoardDAO;

	@Transactional(readOnly = true)
	@Override
	public GeneralPage findById(Long id) {

		GeneralPage generalPage = generalPageDAO.findById(id);

		if (generalPage != null) {

			generalPage.getTaskBoards().size();

		}

		return generalPage;
	}

	@Transactional
	@Override
	public void save(GeneralPage generalPage) {

		generalPageDAO.save(generalPage);

	}

	@Transactional
	@Override
	public void addTaskBoardToGeneralPage(Long generalPageId, TaskBoard taskBoard) {

		GeneralPage generalPage = generalPageDAO.findById(generalPageId);

		if (generalPage == null) {

			System.out.println("GeneralPage not found for id: " + generalPageId);

			return;
		}
		taskBoard.setGeneralPage(generalPage);

		System.out.println("TaskBoard before save - Name: " + taskBoard.getName() + ", Description: "
				+ taskBoard.getDescription() + ", GeneralPage ID: " + taskBoard.getGeneralPage().getId());

		taskBoardDAO.save(taskBoard);

		System.out.println("TaskBoard saved with id: " + taskBoard.getId() + " and generalPage id: "
				+ taskBoard.getGeneralPage().getId());
	}
}
