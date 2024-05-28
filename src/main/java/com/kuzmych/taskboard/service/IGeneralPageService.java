package com.kuzmych.taskboard.service;



import com.kuzmych.taskboard.entity.GeneralPage;

public interface IGeneralPageService {

	GeneralPage findById(Long id);

	void save(GeneralPage generalPage);

	void delete(Long id);

}
