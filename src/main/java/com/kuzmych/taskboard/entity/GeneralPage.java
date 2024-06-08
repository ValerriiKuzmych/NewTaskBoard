package com.kuzmych.taskboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "general_page")
public class GeneralPage {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	private long id;
	
	

	public GeneralPage() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
