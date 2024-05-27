package com.kuzmych.taskboard.service;

import java.util.List;

import com.kuzmych.taskboard.entity.GeneralPage;

public interface IGeneralPageService {

	GeneralPage findById(Long id);

	List<GeneralPage> findAll();

	void save(GeneralPage user);

	void delete(Long id);

}
