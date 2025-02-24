package com.kuzmych.taskboard.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class PasswordService implements IPasswordService {
	
	

	private BCryptPasswordEncoder passwordEncoder;

	public PasswordService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public String hashPassword(String plainPassword) {

		return passwordEncoder.encode(plainPassword);
	}

	@Override
	public boolean checkPassword(String plainPassword, String hashedPassword) {

		return passwordEncoder.matches(plainPassword, hashedPassword);
	}

}
