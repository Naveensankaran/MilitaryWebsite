package com.military.app.dto;

public class SentMessageStatusDto {

    private Long messageId;
    private int totalReceivers;
    private int readCount;
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public int getTotalReceivers() {
		return totalReceivers;
	}
	public void setTotalReceivers(int totalReceivers) {
		this.totalReceivers = totalReceivers;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

    // getters & setters
    
    
}

