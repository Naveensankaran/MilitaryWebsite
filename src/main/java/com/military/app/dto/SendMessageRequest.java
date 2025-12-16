package com.military.app.dto;

import lombok.Data;
import java.util.List;

@Data
public class SendMessageRequest {
    private String content;
    private Long senderId;
    private List<Long> receiverIds;
    private List<AttachmentRequest> attachments;
	public SendMessageRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public List<Long> getReceiverIds() {
		return receiverIds;
	}
	public void setReceiverIds(List<Long> receiverIds) {
		this.receiverIds = receiverIds;
	}
	public List<AttachmentRequest> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentRequest> attachments) {
		this.attachments = attachments;
	}
    
    
}
