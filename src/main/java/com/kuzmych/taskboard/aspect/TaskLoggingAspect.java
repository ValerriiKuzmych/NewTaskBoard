package com.kuzmych.taskboard.aspect;

import java.time.LocalDateTime;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.entity.TaskLog;
import com.kuzmych.taskboard.entity.TaskUtils;
import com.kuzmych.taskboard.service.ITaskLogService;
import com.kuzmych.taskboard.service.ITaskService;

@Aspect
@Component
public class TaskLoggingAspect {

	@Autowired
	private ITaskService taskService;

	@Autowired
	private ITaskLogService taskLogService;

	private ThreadLocal<Task> oldTaskThreadLocal = new ThreadLocal<>();

	@Pointcut("execution(* com.kuzmych.taskboard.service.TaskService.update(..))")
	public void taskUpdatePointcut() {
	}

	@Before("taskUpdatePointcut() && args(task,..)")
	public void beforeTaskUpdate(Task task) {
		Task oldTask = TaskUtils.copy(taskService.findById(task.getId()));
		oldTaskThreadLocal.set(oldTask);
	}

	@AfterReturning(value = "taskUpdatePointcut()", returning = "updatedTask")
	public void logTaskChanges(JoinPoint joinPoint, Task updatedTask) {

		if (updatedTask == null) {

			return;
		}
		Task oldTask = oldTaskThreadLocal.get();

		if (oldTask == null) {

			return;
		}
		oldTaskThreadLocal.remove();

		String username = "unknown";

		logFieldChange(updatedTask, username, "title", oldTask.getName(), updatedTask.getName());
		logFieldChange(updatedTask, username, "description", oldTask.getDescription(), updatedTask.getDescription());
		logFieldChange(updatedTask, username, "status", oldTask.getTaskStatus().toString(),
				updatedTask.getTaskStatus().toString());
	}

	private void logFieldChange(Task task, String username, String field, String oldValue, String newValue) {

		if (!Objects.equals(oldValue, newValue)) {

			saveLog(task, username, field, oldValue, newValue);
		}
	}

	private void saveLog(Task task, String username, String field, String oldValue, String newValue) {

		TaskLog log = new TaskLog();

		log.setTask(task);
		log.setChangedBy(username);
		log.setFieldName(field);
		log.setOldValue(oldValue);
		log.setNewValue(newValue);
		log.setChangedAt(LocalDateTime.now());

		taskLogService.save(log);

	}

}
