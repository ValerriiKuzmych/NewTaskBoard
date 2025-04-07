package com.kuzmych.taskboard.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "task_board")
public class TaskBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	private String name;
	private String description;

	@OneToMany(mappedBy = "taskBoard", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Task> tasks;

	@OneToMany(mappedBy = "taskBoard", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<UserTaskBoardAccess> usersWithAccess;

	@ManyToOne
	@JoinColumn(name = "general_page_id")
	@JsonBackReference
	private GeneralPage generalPage;

	@Version
	private Long version;

	public TaskBoard() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public GeneralPage getGeneralPage() {
		return generalPage;
	}

	public void setGeneralPage(GeneralPage generalPage) {
		this.generalPage = generalPage;
	}

	public List<UserTaskBoardAccess> getUsersWithAccess() {
		return usersWithAccess;
	}

	public void setUsersWithAccess(List<UserTaskBoardAccess> usersWithAccess) {
		this.usersWithAccess = usersWithAccess;
	}

}
