package com.kuzmych.taskboard.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration

public class MailConfig {

	@Value("${spring.mail.username}")
	private String mailjetApiKey;

	@Value("${spring.mail.password}")
	private String mailjetSecretKey;

	@Bean
	JavaMailSender getJavaMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("in-v3.mailjet.com");
		mailSender.setPort(587);
		mailSender.setUsername(mailjetApiKey);
		mailSender.setPassword(mailjetSecretKey);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

}
