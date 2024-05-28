package com.kuzmych.taskboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuzmych.taskboard.entity.GeneralPage;
import com.kuzmych.taskboard.service.GeneralPageService;

@RestController
@RequestMapping("/generalPages")
public class GeneralPageController {

	@Autowired
	private GeneralPageService generalPageService;

	@GetMapping("/{id}")
	public GeneralPage getUserById(@PathVariable Long id) {
		return generalPageService.findById(id);
	}

	@PostMapping
	public void createUser(@RequestBody GeneralPage generalPage) {
		generalPageService.save(generalPage);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		generalPageService.delete(id);
	}

}
