package com.military.app.dto;


public class UserRequest {

    private String username;
    private String password;
    private Long roleId;
    private String rankName;
    private String unit;   // ✅ added
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
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRankName() {
		return rankName;
	}
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

   
    
    
    
}
