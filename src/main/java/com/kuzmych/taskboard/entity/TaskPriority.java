package com.kuzmych.taskboard.entity;

public enum TaskPriority {

	LOW, MEDIUM, HIGH;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}