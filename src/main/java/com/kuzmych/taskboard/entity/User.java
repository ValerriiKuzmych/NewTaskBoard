package com.kuzmych.taskboard.entity;

import java.time.LocalDateTime;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "login") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String name;
	private String email;
	private String login;
	private String password;

	private String resetToken;
	private LocalDateTime tokenExpiration;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "general_page_id")
	@JsonManagedReference
	private GeneralPage generalPage;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<UserTaskBoardAccess> taskBoardAccesses;

	@Version
	private Long version;

	public User() {

	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GeneralPage getGeneralPage() {
		return generalPage;
	}

	public void setGeneralPage(GeneralPage generalPage) {
		this.generalPage = generalPage;
	}

	public List<UserTaskBoardAccess> getTaskBoardAccesses() {
		return taskBoardAccesses;
	}

	public void setTaskBoardAccesses(List<UserTaskBoardAccess> taskBoardAccesses) {
		this.taskBoardAccesses = taskBoardAccesses;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getTokenExpiration() {
		return tokenExpiration;
	}

	public void setTokenExpiration(LocalDateTime tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}

}
