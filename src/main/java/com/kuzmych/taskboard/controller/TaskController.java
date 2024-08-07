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

		Task task = taskService.findById(id);
		taskService.delete(id);

		return "redirect:/taskboards/show/" + task.getTaskBoard().getId();
	}

	@GetMapping("/edit/{id}")
	public String editTaskForm(@PathVariable Long id, Model model) {

		Task task = taskService.findById(id);

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
	public String updateTask(@ModelAttribute Task task, Model model) {
		TaskBoard taskBoard = task.getTaskBoard();

		if (taskBoard == null) {
			model.addAttribute("error", "TaskBoard is not set for the Task.");
			return "task/edit";
		}

		Long taskBoardId = taskBoard.getId();
		System.out.println("TaskBoard ID: " + taskBoardId);

		try {

			taskService.update(task);

		} catch (EntityNotFoundException e) {
			model.addAttribute("error", "Task not found. Please try again.");
			return "task/edit";
		} catch (OptimisticLockingFailureException e) {
			model.addAttribute("error", "Another user has updated the record. Please refresh and try again.");
			return "task/edit";
		}

		System.out.println("Redirecting to /tasks/show/" + taskBoardId);

		return "redirect:/taskboards/show/" + taskBoardId;
	}

}