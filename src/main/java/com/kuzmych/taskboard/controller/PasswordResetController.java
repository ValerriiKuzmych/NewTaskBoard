package com.kuzmych.taskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuzmych.taskboard.service.IPasswordResetService;

@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

	@Autowired
	private IPasswordResetService passwordResetService;

	@GetMapping("/show")
	public String showResetPasswordPage() {
		// TODO Auto-generated method stub
		return "TO-DO";
	};

	@PostMapping
	public String processResetRequest(@RequestParam String email, Model model) {

		// TODO Auto-generated method stub

		return "TO-DO";
	};

	@GetMapping("/confirm")
	public String showNewPasswordForm(@RequestParam String token, Model model) {

		// TODO Auto-generated method stub

		return "TO-DO";
	}

	@PostMapping("/confirm")
	public String processNewPassword(@RequestParam String token, @RequestParam String password, Model model) {

		// TODO Auto-generated method stub

		return "TO-DO";
	}

}
