package com.kuzmych.taskboard.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "general_page")
public class GeneralPage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@OneToMany(mappedBy = "generalPage", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskBoard> taskBoards;

	@OneToOne
	@JoinColumn(name = "user_id") // This creates a foreign key column in the 'general_page' table.
	private User user;
	

	public GeneralPage() {

	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<TaskBoard> getTaskBoards() {
		return taskBoards;
	}

	public void setTaskBoards(List<TaskBoard> taskBoards) {
		this.taskBoards = taskBoards;
	}

}
