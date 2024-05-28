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

import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.service.TaskBoardService;

@RestController
@RequestMapping("/taskBoards")
public class TaskBoardController {

	@Autowired
	private TaskBoardService taskBoardService;

	@GetMapping("/{id}")
	public TaskBoard getUserById(@PathVariable Long id) {
		return taskBoardService.findById(id);
	}

	@GetMapping
	public List<TaskBoard> getAll() {
		return taskBoardService.findAll();
	}

	@PostMapping
	public void createUser(@RequestBody TaskBoard taskBoard) {
		taskBoardService.save(taskBoard);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		taskBoardService.delete(id);
	}

}
