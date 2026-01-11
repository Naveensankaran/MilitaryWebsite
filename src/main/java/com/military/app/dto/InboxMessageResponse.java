package com.military.app.dto;

import java.time.LocalDateTime;

public class InboxMessageResponse {

    private Long messageId;
    private String senderUsername;
    private String senderRole;
    private String senderRank;
    private String senderUnit;
    private String content;      // decrypted
    private LocalDateTime sentAt;
    private boolean readStatus;
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getSenderUsername() {
		return senderUsername;
	}
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	public String getSenderRole() {
		return senderRole;
	}
	public void setSenderRole(String senderRole) {
		this.senderRole = senderRole;
	}
	public String getSenderRank() {
		return senderRank;
	}
	public void setSenderRank(String senderRank) {
		this.senderRank = senderRank;
	}
	public String getSenderUnit() {
		return senderUnit;
	}
	public void setSenderUnit(String senderUnit) {
		this.senderUnit = senderUnit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getSentAt() {
		return sentAt;
	}
	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	public boolean isReadStatus() {
		return readStatus;
	}
	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}

    
    // getters & setters
}