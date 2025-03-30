package com.kuzmych.taskboard.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuzmych.taskboard.dao.ITaskDAO;
import com.kuzmych.taskboard.entity.Task;

@Transactional
@Service
public class TaskService implements ITaskService {

	@Autowired
	private ITaskDAO taskDAO;

	@Transactional(readOnly = true)
	@Override
	public Task findById(Long id) {

		return taskDAO.findById(id);
	}

	@Transactional
	@Override
	public List<Task> findAll() {
		return taskDAO.findAll();
	}

	@Transactional
	@Override
	public void save(Task task) {

		task.setCreatedDate(LocalDateTime.now());
		taskDAO.save(task);
	}

	@Override
	public void delete(Long taskId) {

		Task task = taskDAO.findById(taskId);

		if (task != null) {

//			List<TaskLog> logs = taskLogDAO.getAllLogs(taskId);
//
//			try {
//				String logsJson = new ObjectMapper().writeValueAsString(logs);
//
//				TaskBoardLog logEntry = new TaskBoardLog();
//				logEntry.setAction("TASK_DELETED");
//				logEntry.setId(taskId);
//				// TODO
//				logEntry.setChangedBy("Unknown");
//				logEntry.setDetails(logsJson);
//
//				taskBoardLogDAO.save(logEntry);
//
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			try {
				taskDAO.deleteById(taskId);
			} catch (OptimisticLockingFailureException ex) {
				throw new RuntimeException("Failed to delete task  due to optimistic locking conflict");
			}
		} else {
			throw new EntityNotFoundException("Task not found with ID: " + taskId);
		}
	}

	@Transactional
	@Override
	public Task update(Task task) {

		Task existingTask = taskDAO.findById(task.getId());

		if (existingTask != null) {
			existingTask.setName(task.getName());
			existingTask.setDescription(task.getDescription());
			existingTask.setVersion(task.getVersion());
			existingTask.setFilePath(task.getFilePath());
			existingTask.setPriority(task.getPriority());
			existingTask.setTaskStatus(task.getTaskStatus());
			existingTask.setExecutorName(task.getExecutorName());
			existingTask.setDeadlineDate(task.getDeadlineDate());
			taskDAO.update(existingTask);

			return existingTask;

		} else {
			throw new EntityNotFoundException("Task not found with ID: " + task.getId());
		}

	}

	@Override
	public String getTimeLeft(Task task) {

		if (task == null || task.getDeadlineDate() == null) {
			return null;
		}
		LocalDateTime now = LocalDateTime.now();

		LocalDateTime deadline = task.getDeadlineDate();

		if (deadline.isBefore(now)) {

			Duration overdue = Duration.between(deadline, now);
			long overdueDays = overdue.toDays();
			return "Overdue by " + overdueDays + " days";
		}

		Duration duration = Duration.between(now, deadline);

		if (duration.isZero() || duration.isNegative()) {
			return "Deadline is now!";
		}

		long days = duration.toDays();
		long hours = duration.toHours() % 24;
		long minutes = duration.toMinutes() % 60;

		return String.format("%d days, %02d:%02d", days, hours, minutes);
	}

}
