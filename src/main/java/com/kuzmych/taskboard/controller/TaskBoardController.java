package com.kuzmych.taskboard.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.service.ITaskBoardService;

@Controller
@RequestMapping("/taskboards")
public class TaskBoardController {

	@Autowired
	private ITaskBoardService taskBoardService;

	@GetMapping("/show/{id}")
	public String showTaskBoard(@PathVariable Long id, Model model) {
		TaskBoard taskBoard = taskBoardService.findById(id);
		model.addAttribute("taskBoard", taskBoard);
		return "taskboard/show";
	}

	 @GetMapping
	    public String listTaskBoards(Model model) {
	        List<TaskBoard> taskBoards = taskBoardService.findAll();
	        model.addAttribute("taskBoards", taskBoards);
	        return "taskboard/taskboards"; // Ensure the template exists
	    }


	@GetMapping("/new")

	public String showCreateForm(Model model) {

		model.addAttribute("taskBoard", new TaskBoard());

		return "taskboard/new";

	}

	@PostMapping

	public String createTaskBoard(@ModelAttribute TaskBoard taskBoard) {

		taskBoardService.save(taskBoard);

		return "redirect:/taskboards";
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		taskBoardService.delete(id);
	}

	@GetMapping("/edit/{id}")
	public String editTaskBoardForm(@PathVariable Long id, Model model) {

		TaskBoard taskBoard = taskBoardService.findById(id);

		if (taskBoard != null) {

			model.addAttribute("taskBoard", taskBoard);

			return "taskboard/edit"; // Make sure this matches the actual template name

		} else {

			return "redirect:/taskboards";

		}
	}

	@PostMapping("/update")
	public String updateTaskBoard(@ModelAttribute TaskBoard taskBoard, Model model) {
		try {
			taskBoardService.update(taskBoard);
		} catch (EntityNotFoundException e) {
			model.addAttribute("error", "TaskBoard not found. Please try again.");
			return "taskboard/edit";
		} catch (OptimisticLockingFailureException e) {
			model.addAttribute("error", "Another user has updated the record. Please refresh and try again.");
			return "taskboard/edit";
		}
		return "redirect:/taskboards";
	}
}
