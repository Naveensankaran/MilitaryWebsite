package com.military.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
//import lombok.*;

@Entity

public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private boolean success;

    private String ipAddress;

    private LocalDateTime attemptTime = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LocalDateTime getAttemptTime() {
		return attemptTime;
	}

	public void setAttemptTime(LocalDateTime attemptTime) {
		this.attemptTime = attemptTime;
	}

	public LoginAttempt() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
