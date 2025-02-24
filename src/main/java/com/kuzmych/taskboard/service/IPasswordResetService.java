package com.kuzmych.taskboard.service;

public interface IPasswordResetService {
	
	boolean sendPasswordResetEmail(String email);
	boolean isValidToken(String token);
	boolean resetPassword(String token, String newPassword);
	
		
	
	

}
