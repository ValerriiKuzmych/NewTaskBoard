package com.kuzmych.taskboard.dao;

import java.util.List;

import com.kuzmych.taskboard.entity.GeneralPage;

public interface IGeneralPageDAO {

	GeneralPage findById(Long id);

	List<GeneralPage> findAll();

	void save(GeneralPage user);

	void deleteById(Long id);

}
