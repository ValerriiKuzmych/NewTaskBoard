package com.kuzmych.taskboard.entity;

public class TaskUtils {
	
	
	private TaskUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

	public static Task copy(Task original) {

		if (original == null) {
			return null;

		}

		Task copy = new Task();

		copy.setName(original.getName());
		copy.setDescription(original.getDescription());
		copy.setPriority(original.getPriority());
		copy.setTaskStatus(original.getTaskStatus());
		copy.setExecutorName(original.getExecutorName());
		copy.setDeadlineDate(original.getDeadlineDate());
		copy.setFilePath(original.getFilePath());

		return copy;

	}

}
