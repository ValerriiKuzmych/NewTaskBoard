  package com.kuzmych.taskboard.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kuzmych.taskboard.entity.User;

@Service
public class PasswordResetService implements IPasswordResetService {

	@Autowired
	private IUserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordService passwordService;

	@Override
	public boolean sendPasswordResetEmail(String email) {

		User user = userService.findByUserEmail(email);

		if (user == null) {
			return false;
		}

		String token = UUID.randomUUID().toString();

		user.setResetToken(token);
		user.setTokenExpiration(LocalDateTime.now().plusHours(1));

		userService.updatePassword(user);

		String resetLink = "http://localhost:8080/taskboardmanager/reset-password/confirm?token=" + token;

		sendEmail(user.getEmail(), resetLink);

		return true;
	}

	@Override
	public boolean isValidToken(String token) {

		User user = userService.findByUserResetToken(token);

		if (user == null) {
			return false;
		}

		return user.getTokenExpiration().isAfter(LocalDateTime.now());
	}

	@Override
	public boolean resetPassword(String token, String newPassword) {

		User user = userService.findByUserResetToken(token);

		if (user == null) {

			return false;
		}

		if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {

			return false;
		}

		user.setPassword(passwordService.hashPassword(newPassword));
		user.setResetToken(null);
		user.setTokenExpiration(null);
		userService.updatePassword(user);

		return true;
	}

	private void sendEmail(String to, String resetLink) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Password Reset Request");
		message.setText("Click the link to reset your password: " + resetLink);
		//enter email
		message.setFrom("");
		mailSender.send(message);

	};

}
