package com.kuzmych.taskboard.config;

import javax.annotation.PostConstruct;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityConfig {

	 @PostConstruct
	    public void init() {
	        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	    }
}
