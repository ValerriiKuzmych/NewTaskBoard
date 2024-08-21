package com.kuzmych.taskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kuzmych.taskboard.entity.User;
import com.kuzmych.taskboard.service.IUserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/{id}")
	public String getUserDetails(@PathVariable Long id, Model model) {

		User user = userService.findById(id);

		model.addAttribute("user", user);

		return "user-details";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {

		model.addAttribute("user", new User());

		return "user/user-registration";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult result) {

		if (result.hasErrors()) {

			return "user/user-registration";
		}

		userService.save(user);

		return "redirect:/user/user-details/" + user.getId();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		userService.delete(id);
	}
}