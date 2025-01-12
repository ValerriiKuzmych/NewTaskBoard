package com.kuzmych.taskboard.entity;

public enum TaskStatus {
	NEW, IN_PROGRESS, COMPLETED;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
