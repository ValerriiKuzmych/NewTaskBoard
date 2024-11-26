package com.kuzmych.taskboard.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.service.ITaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ITaskService taskService;

	@GetMapping("/show/{id}")
	public String showTaskBoard(@PathVariable Long id, Model model) {
		Task task = taskService.findById(id);
		System.out.println("!!!!!!!!!!!!!!!" + task.getTaskBoard().getGeneralPage().getId());
		model.addAttribute("task", task);
		return "task/show";
	}

	@GetMapping
	public String listTaskBoards(Model model) {
		List<Task> tasks = taskService.findAll();
		model.addAttribute("tasks", tasks);
		return "tasks";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("task", new Task());
		return "task/new";
	}

	@PostMapping
	public String createTask(@ModelAttribute Task task) {
		taskService.save(task);
		return "redirect:/tasks";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {

		Task task = taskService.findById(id);
		taskService.delete(id);

		return "redirect:/taskboards/show/" + task.getTaskBoard().getId();
	}

	@GetMapping("/edit/{id}")
	public String editTaskForm(@PathVariable Long id, Model model) {
		Task task = taskService.findById(id);

		System.out.println("Editing Task ID: " + id + " - Task: " + task);

		if (task == null) {
			return "error/404";
		}

		TaskBoard taskBoard = task.getTaskBoard();

		if (taskBoard == null) {
			return "error/404";
		}

		model.addAttribute("task", task);
		model.addAttribute("taskBoard", taskBoard);

		return "task/edit";
	}

	@PostMapping("/update")
	public String updateTask(@RequestParam("id") Long id, @ModelAttribute Task task,
			@RequestParam(value = "file", required = false) MultipartFile file, Model model) {

		task.setId(id);
		System.out.println("Updating Task ID: " + task.getId());
		System.out.println("Uploading File: " + file);

		if (file != null && !file.isEmpty()) {
			try {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = Paths.get("C:\\TaskBoard\\uploads_files_for_tasks\\" + fileName);
				System.out.println("filepath: " + filePath);
				Files.write(filePath, file.getBytes());
				task.setFilePath(filePath.toString());
			} catch (IOException e) {
				model.addAttribute("error", "File upload failed: " + e.getMessage());
				return "task/edit";
			}
		}

		taskService.update(task);
		return "redirect:/taskboards/show/" + taskService.findById(id).getTaskBoard().getId();
	}

}