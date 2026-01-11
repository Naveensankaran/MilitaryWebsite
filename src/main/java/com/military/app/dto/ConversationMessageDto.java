package com.military.app.dto;

import java.time.LocalDateTime;

public class ConversationMessageDto {

    private Long messageId;
    private Long senderId;
    private String senderName;
    private String senderRole;
    private String senderRank;
    private String senderUnit;
    private String content;
    private LocalDateTime sentAt;
    private boolean readStatus;
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
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
}
