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

		return "reset-password/show-reset-pasword-form";
	};

	@PostMapping("/show")
	public String processResetRequest(@RequestParam String email, Model model) {

		boolean emailSent = passwordResetService.sendPasswordResetEmail(email);

		if (emailSent) {
			model.addAttribute("message", "Check your email for the reset link.");

		} else {

			model.addAttribute("error", "Email not found.");

		}

		return "reset-password/message";
	};

	@GetMapping("/confirm")
	public String showNewPasswordForm(@RequestParam String token, Model model) {
		
		if (!passwordResetService.isValidToken(token)) {

			model.addAttribute("error", "Invalid or expired token.");
		}

		model.addAttribute("token", token);

		return "reset-password/show-new-password-form";
	}

	@PostMapping("/confirm")
	public String processNewPassword(@RequestParam String token, @RequestParam String password, Model model) {

		boolean success = passwordResetService.resetPassword(token, password);

		if (success) {
			return "redirect:/users/login";
		} else {

			model.addAttribute("error", "invalid token or expired link.");

			return "reset-password-error";
		}

	}

}
