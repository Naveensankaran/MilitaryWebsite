package com.military.app.dto;


public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public ChangePasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
