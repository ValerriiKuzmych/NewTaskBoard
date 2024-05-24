package com.kuzmych.taskboard.dao;

import java.util.List;

public interface IEntityDAO<T> {
	
	    T findById(Long id);
	    List<T> findAll();
	    void save(T user);
	    void deleteById(Long id);

}
