package com.kuzmych.taskboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/{id}")
	public Task getUserById(@PathVariable Long id) {
		return taskService.findById(id);
	}

	@GetMapping
	public List<Task> getAll() {
		return taskService.findAll();
	}

	@PostMapping
	public void createUser(@RequestBody Task task) {
		taskService.save(task);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		taskService.delete(id);
	}
}