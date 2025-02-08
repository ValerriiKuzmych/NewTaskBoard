package com.kuzmych.taskboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuzmych.taskboard.entity.TaskBoard;
import com.kuzmych.taskboard.entity.UserTaskBoardAccess;
import com.kuzmych.taskboard.service.ITaskBoardService;
import com.kuzmych.taskboard.service.IUserService;
import com.kuzmych.taskboard.service.IUserTaskBoardAccessService;

@Controller
@RequestMapping("/users-access")
public class UserTaskBoardAccessController {

	@Autowired
	ITaskBoardService taskBoardService;

	@Autowired
	IUserService userService;

	@Autowired
	IUserTaskBoardAccessService usertaskBoardAccessServcie;

	@GetMapping("/{id}/access-option")
	public String showUsersAccessForm(@PathVariable Long id, Model model, HttpSession session) {

//		User currentUser = (User) session.getAttribute("currentUser");
//
//		if (currentUser == null) {
//
//			return "redirect:/users/login";
//		}
//
		TaskBoard taskBoard = taskBoardService.findById(id);
//
//		if (!taskBoard.getGeneralPage().getUser().equals(currentUser)) {
//
//			return "error-unauthorized";
//		}

//		User targetUser = null;
//
//		if (userIdentifier != null && !userIdentifier.trim().isEmpty()) {
//			targetUser = userService.findByNameOrId(userIdentifier);
//
//		}
		model.addAttribute("taskBoard", taskBoard);
//		model.addAttribute("targetUser", targetUser);
		model.addAttribute("taskBoardAccess", new UserTaskBoardAccess());

		return "users-access/access-form";

	}

	@PostMapping("/{id}/access-option")
	public String giveUsersAccess(@PathVariable Long id, @RequestParam String userIdentifier,
			@ModelAttribute UserTaskBoardAccess taskBoardacces, HttpSession session) {

//		User currentUser = (User) session.getAttribute("currentUser");
//
//		if (currentUser == null) {
//
//			return "redirect:/users/login";
//		}

		

//		if (!taskBoard.getGeneralPage().getUser().equals(currentUser)) {
//
//			return "error-unauthorized";
//		}

		usertaskBoardAccessServcie.giveAccessToTaskBoard(id, userIdentifier, taskBoardacces);

		return "redirect:/taskboards/show/" + id;

	}

}
