package com.kuzmych.taskboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.service.IGeneralPageService;

@Controller
@RequestMapping("/generalpage")
public class GeneralPageController {

	@Autowired
	private IGeneralPageService generalPageService;

	@GetMapping("/show/{id}")
	public String showGeneralPage(@PathVariable Long id, Model model) {

		GeneralPage generalPage = generalPageService.findById(id);

		if (generalPage == null) {

			return "error/404";
		}
		
		 List<TaskBoard> taskBoards = generalPage.getTaskBoards();
		 
		    model.addAttribute("generalPage", generalPage);
		    
		    model.addAttribute("taskBoards", taskBoards);
		    
		return "generalpage/show-generalpage";
	}

//	@GetMapping("/new")
//
//	public String showCreateGeneralPageForm(Model model) {
//
//		model.addAttribute("generalPage", new GeneralPage());
//
//		return "generalpage/new-generalpage";
//	}
//
//	@PostMapping
//	public String createGeneralPage(@ModelAttribute GeneralPage generalPage) {
//
//		generalPageService.save(generalPage);
//
//		return "redirect:/generalpage";
//	}

	@GetMapping("/{id}/taskboards/new")
	public String showAddTaskBoardForm(@PathVariable Long id, Model model) {

		model.addAttribute("taskBoard", new TaskBoard());

		model.addAttribute("generalPageId", id);

		return "generalpage/add-taskboard";
	}

	@PostMapping("/{id}/taskboards")
	public String addTaskBoardToGeneralPage(@PathVariable Long id, @ModelAttribute TaskBoard taskBoard) {

		System.out.println("Adding TaskBoard to GeneralPage with id: " + id);

		generalPageService.addTaskBoardToGeneralPage(id, taskBoard);

		return "redirect:/generalpage/show/" + id;
	}
}
