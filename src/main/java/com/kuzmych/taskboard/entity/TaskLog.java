package com.kuzmych.taskboard.entity;

import lombok.Data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "task_logs")
public class TaskLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "task_id", nullable = true)
	private Task task;

	@Column(nullable = false)
	private String changedBy;

	@Column(nullable = false)
	private String fieldName;

	@Column(columnDefinition = "TEXT")
	private String oldValue;

	@Column(columnDefinition = "TEXT")
	private String newValue;

	@Column(nullable = false)
	private LocalDateTime changedAt = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		this.changedAt = changedAt;
	}

}
