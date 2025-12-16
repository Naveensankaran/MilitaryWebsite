package com.military.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    public LoginResponse(String string, Object object) {
		// TODO Auto-generated constructor stub
	}
	private String message;
    private Long userId;
}
