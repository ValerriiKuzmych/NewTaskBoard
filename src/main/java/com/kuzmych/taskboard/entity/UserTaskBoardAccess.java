package com.kuzmych.taskboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "user_taskboard_access")
public class UserTaskBoardAccess {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "taskboard_id")
    private TaskBoard taskBoard;

	private boolean creatingNewTask;
	private boolean deletingTask;
	private boolean editingTask;
	private boolean readingTask;

	
	@Version
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isCreatingNewTask() {
		return creatingNewTask;
	}

	public void setCreatingNewTask(boolean creatingNewTask) {
		this.creatingNewTask = creatingNewTask;
	}

	public boolean isDeletingTask() {
		return deletingTask;
	}

	public void setDeletingTask(boolean deletingTask) {
		this.deletingTask = deletingTask;
	}

	public boolean isEditingTask() {
		return editingTask;
	}

	public void setEditingTask(boolean editingTask) {
		this.editingTask = editingTask;
	}

	public boolean isReadingTask() {
		return readingTask;
	}

	public void setReadingTask(boolean readingTask) {
		this.readingTask = readingTask;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TaskBoard getTaskBoard() {
		return taskBoard;
	}

	public void setTaskBoard(TaskBoard taskBoard) {
		this.taskBoard = taskBoard;
	}

}
