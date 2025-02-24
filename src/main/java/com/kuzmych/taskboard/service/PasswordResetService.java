package com.kuzmych.taskboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kuzmych.taskboard.dao.IUserDAO;

public class PasswordResetService implements IPasswordResetService{
	
	
	@Autowired
    private IUserDAO userDAO;

    @Autowired
    private JavaMailSender mailSender;
    
    private BCryptPasswordEncoder passwordEncoder;
    
    public PasswordResetService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public boolean sendPasswordResetEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValidToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean resetPassword(String token, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}
	
	 private void sendEmail(String to, String resetLink) {
		 
		// TODO Auto-generated method stub
	 };

}
