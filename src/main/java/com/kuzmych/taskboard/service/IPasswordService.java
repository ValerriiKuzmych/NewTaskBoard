package com.kuzmych.taskboard.service;

public interface IPasswordService {

	public String hashPassword(String plainPassword);

	public boolean checkPassword(String plainPassword, String hashedPassword);

}
