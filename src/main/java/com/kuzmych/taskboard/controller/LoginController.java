package com.kuzmych.taskboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

@RequestMapping("/loginpage")
public class LoginController {

	@GetMapping("/login")
	public String showLoginForm() {

		return "/login/login";

	}

	@PostMapping
	public String login(@RequestParam String username, @RequestParam String password) {

		boolean isAuthenticated = authenticateUser(username, password);

		if (isAuthenticated) {
			return "redirect:/taskboard/generalpage/";
		} else {
			return "/login";
		}
	}

	private boolean authenticateUser(String username, String password) {
		// Implement your authentication logic here
		return true; // Placeholder

	}

}
