package com.military.app.dto;


import java.util.List;


public class BroadcastRankRequest {
    private String rank;          // Captain, Major, Soldier
    private String content;
    private List<AttachmentRequest> attachments;
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<AttachmentRequest> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<AttachmentRequest> attachments) {
		this.attachments = attachments;
	}
    
    
}
