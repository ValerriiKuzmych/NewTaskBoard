package com.kuzmych.taskboard.controller;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.entity.Task;
import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.entity.TaskPriority;
import com.kuzmych.taskboard.entity.TaskStatus;
import com.kuzmych.taskboard.entity.User;
import com.kuzmych.taskboard.service.ITaskBoardService;

@Controller
@RequestMapping("/taskboards")
public class TaskBoardController {

	@Autowired
	private ITaskBoardService taskBoardService;

	@GetMapping("/show/{id}")
	public String showTaskBoard(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		TaskBoard taskBoard = taskBoardService.findById(id);

		if (taskBoard == null || !taskBoard.getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());
		model.addAttribute("statuses", statuses);
		model.addAttribute("taskBoard", taskBoard);
		return "taskboard/show";
	}

	@PostMapping
	public String createTaskBoard(@ModelAttribute TaskBoard taskBoard) {
		taskBoardService.save(taskBoard);
		return "redirect:/taskboards";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		TaskBoard taskBoard = taskBoardService.findById(id);

		if (taskBoard == null || !taskBoard.getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		taskBoardService.delete(id);

		return "redirect:/generalpage/show/" + taskBoard.getGeneralPage().getId();
	}

	@GetMapping("/edit/{id}")
	public String editTaskBoardForm(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		TaskBoard taskBoard = taskBoardService.findById(id);

		if (taskBoard == null || !taskBoard.getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		GeneralPage generalPage = taskBoard.getGeneralPage();

		if (generalPage == null) {
			return "error/404";
		}

		model.addAttribute("taskBoard", taskBoard);
		model.addAttribute("generalPage", generalPage);

		return "taskboard/edit";
	}

	@PostMapping("/update")
	public String updateTaskBoard(@ModelAttribute TaskBoard taskBoard, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		TaskBoard taskBoardchek = taskBoard;

		if (taskBoard == null || !taskBoardchek.getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}

		GeneralPage generalPage = taskBoard.getGeneralPage();

		if (generalPage == null) {
			model.addAttribute("error", "GeneralPage is not set for the TaskBoard.");
			return "taskboard/edit";
		}

		Long generalPageId = generalPage.getId();
		System.out.println("GeneralPage ID: " + generalPageId);

		try {

			taskBoardService.update(taskBoard);

		} catch (EntityNotFoundException e) {
			model.addAttribute("error", "TaskBoard not found. Please try again.");
			return "taskboard/edit";
		} catch (OptimisticLockingFailureException e) {
			model.addAttribute("error", "Another user has updated the record. Please refresh and try again.");
			return "taskboard/edit";
		}

		System.out.println("Redirecting to /generalpage/show/" + generalPageId);

		return "redirect:/generalpage/show/" + generalPageId;
	}

	@GetMapping("/{id}/tasks/new")
	public String showAddTaskForm(@PathVariable Long id, Model model, HttpSession session) {

		User loggedInUser = (User) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/users/login";
		}

		TaskBoard taskBoard = taskBoardService.findById(id);

		if (taskBoard == null || !taskBoard.getGeneralPage().getUser().getLogin().equals(loggedInUser.getLogin())) {
			return "error/403";
		}
		model.addAttribute("task", new Task());
		model.addAttribute("taskBoardId", id);
		return "taskboard/add-task";
	}

	@PostMapping("/{id}/tasks")
	public String addTaskToTaskBoard(@PathVariable Long id, @Valid @ModelAttribute Task task, BindingResult result) {

		if (task.getTaskStatus() == null) {
			task.setTaskStatus(TaskStatus.NEW);
		}
		if (task.getPriority() == null) {
			task.setPriority(TaskPriority.LOW);
		}
		if (result.hasErrors()) {

			return "taskboard/add-task";
		}
		taskBoardService.addTaskToTaskBoard(id, task);
		return "redirect:/taskboards/show/" + id;
	}

}
