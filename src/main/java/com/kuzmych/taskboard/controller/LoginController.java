package com.kuzmych.taskboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuzmych.taskboard.service.IUserService;

@Controller

@RequestMapping("/loginpage")
public class LoginController {

	private IUserService userService;

	@GetMapping("/login")
	public String showUserLoginForm() {

		return "/login/login";

	}

	@PostMapping
	public String userLogin(@RequestParam String username, @RequestParam String password) {

		boolean isAuthenticated = userService.authenticateUser(username, password);

		if (isAuthenticated) {
			return "redirect:/taskboard/generalpage/";
		} else {
			return "/login";
		}
	}

}
