package com.kuzmych.taskboard.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.service.ITaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private ITaskService taskService;

	@GetMapping("/show/{id}")
	public String showTaskBoard(@PathVariable Long id, Model model) {
		Task task = taskService.findById(id);
		model.addAttribute("taskBoard", task);
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

		taskService.delete(id);
		return "redirect:/tasks";
	}

	@GetMapping("/edit/{id}")
	public String editTaskForm(@PathVariable Long id, Model model) {

		Task task = taskService.findById(id);

		if (task != null) {

			model.addAttribute("task", task);

			return "task/edit";

		} else {

			return "redirect:/edit";

		}
	}

	@PostMapping("/update")
	public String updateTask(@ModelAttribute Task task, Model model) {
		try {
			taskService.update(task);
		} catch (EntityNotFoundException e) {
			model.addAttribute("error", "Task not found. Please try again.");
			return "task/edit";
		} catch (OptimisticLockingFailureException e) {
			model.addAttribute("error", "Another user has updated the record. Please refresh and try again.");
			return "task/edit";
		}
		return "redirect:/tasks";
	}

}