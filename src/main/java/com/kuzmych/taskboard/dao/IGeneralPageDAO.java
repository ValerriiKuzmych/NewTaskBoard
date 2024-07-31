package com.kuzmych.taskboard.dao;

import com.kuzmych.taskboard.entity.GeneralPage;

public interface IGeneralPageDAO {

	GeneralPage findById(Long id);

	void save(GeneralPage generalPage);


	void update(GeneralPage generalPage);

}
