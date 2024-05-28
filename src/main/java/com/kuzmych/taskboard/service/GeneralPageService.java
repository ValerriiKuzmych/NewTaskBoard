package com.kuzmych.taskboard.service;

import org.springframework.stereotype.Service;

import com.kuzmych.taskboard.dao.GeneralPageDAO;
import com.kuzmych.taskboard.entity.GeneralPage;

@Service
public class GeneralPageService implements IGeneralPageService {

	GeneralPageDAO generalPageDAO;

	@Override
	public GeneralPage findById(Long id) {

		return generalPageDAO.findById(id);
	}

	@Override
	public void save(GeneralPage generalPage) {

		generalPageDAO.save(generalPage);

	}

	@Override
	public void delete(Long id) {

		generalPageDAO.deleteById(id);

	}

}
