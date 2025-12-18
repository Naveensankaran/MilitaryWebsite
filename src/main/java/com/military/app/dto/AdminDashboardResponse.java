package com.military.app.dto;

import lombok.Data;

@Data
public class AdminDashboardResponse {

    private long totalUsers;
    private long activeUsers;

    private long totalMessages;
    private long unreadMessages;

    private long totalAttachments;

    private long totalAuditLogs;

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(long activeUsers) {
		this.activeUsers = activeUsers;
	}

	public long getTotalMessages() {
		return totalMessages;
	}

	public void setTotalMessages(long totalMessages) {
		this.totalMessages = totalMessages;
	}

	public long getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(long unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	public long getTotalAttachments() {
		return totalAttachments;
	}

	public void setTotalAttachments(long totalAttachments) {
		this.totalAttachments = totalAttachments;
	}

	public long getTotalAuditLogs() {
		return totalAuditLogs;
	}

	public void setTotalAuditLogs(long totalAuditLogs) {
		this.totalAuditLogs = totalAuditLogs;
	}
    
    
}
