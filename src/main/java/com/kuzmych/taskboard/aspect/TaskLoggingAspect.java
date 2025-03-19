package com.kuzmych.taskboard.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.repository.TaskLogRepository;
import com.kuzmych.taskboard.service.ITaskService;

@Aspect
@Component
public class TaskLoggingAspect {

	private final TaskLogRepository taskLogRepository;

	private ITaskService taskService;

	public TaskLoggingAspect(TaskLogRepository taskLogRepository) {

		this.taskLogRepository = taskLogRepository;
	}

	@Pointcut("execuition(* com.kuzmych.taskboard.service.TaskService.update(..))")
	public void taskUpdatePointcut() {
	}

	@AfterReturning(value = "taskUpdatePointcut()", returning = "updatedTask")
	public void logTaskChanges(JoinPoint joinPoint, Task updatedTask) {

		if (updatedTask == null) {

			return;
		}

		Long taskId = updatedTask.getId();

		Task oldTask = taskService.findById(taskId);

		if (oldTask == null) {

			return;
		}

		// TODO
		String username = "unknown";

	}

}
