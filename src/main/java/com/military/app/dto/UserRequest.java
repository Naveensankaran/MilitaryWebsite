package com.military.app.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String role;      // ADMIN, COMMANDER, SOLDIER
    private String rankName; // Optional: Captain, Major, etc.
    private Long roleId;
	public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	} 
    
    
}
