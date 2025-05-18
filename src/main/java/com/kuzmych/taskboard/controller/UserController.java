package com.kuzmych.taskboard.controller;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.kuzmych.taskboard.dto.UserRegistrationDTO;
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

		if (user != null) {

			model.addAttribute("user", user);

			return "user/user-details";

		} else {

			return "error/404";
		}
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {

		model.addAttribute("userDTO", new UserRegistrationDTO());

		return "user/user-registration";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userDTO") UserRegistrationDTO userDTO, BindingResult result,
			Model model) {

		if (userService.isLoginOrEmailTaken(userDTO.getLogin(), userDTO.getEmail())) {
			model.addAttribute("error", "A user with this login or email already exists.");
			return "user/user-registration";
		}

		if (result.hasErrors()) {

			return "user/user-registration";
		}

		User user = userService.convertingToUser(userDTO);

		userService.save(user);

		return "redirect:/users/" + user.getId();
	}

	@GetMapping("/login")
	public String showUserLoginForm() {

		return "/user/login";

	}

	@PostMapping("/login")
	public String userLogin(@RequestParam String userName, @RequestParam String password, HttpSession session) {

		boolean isAuthenticated = userService.authenticateUser(userName, password);

		if (isAuthenticated) {
			User user = userService.findByUserName(userName);
			session.setAttribute("loggedInUser", user);

			return "redirect:/generalpage/show/" + user.getGeneralPage().getId();
		} else {
			return "redirect:/test";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/users/login";
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {

		userService.delete(id);
	}

}