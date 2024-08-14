package com.kuzmych.taskboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kuzmych.taskboard.entity.User;
import com.kuzmych.taskboard.service.IUserService;

public class RegistrationController {

	IUserService userService;

	@Controller
	@RequestMapping("/registrationpage")
	public class LoginController {

		@GetMapping("/registration")
		public String showRegistrationForm() {

			return "/registration/registration";

		}

	}

	@PostMapping
	public String createUser(@ModelAttribute User user) {

		userService.save(user);

		return "redirect:/generalpage/" + user.getGeneralPage().getId();
	}

}
