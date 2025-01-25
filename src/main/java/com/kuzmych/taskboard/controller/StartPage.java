package com.kuzmych.taskboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/startpage")
public class StartPage {

	@GetMapping("/hello")
	public String showHelloForm() {

		return "/startpage/startpage";

	}

}
