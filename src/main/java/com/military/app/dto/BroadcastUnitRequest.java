package com.military.app.dto;

import java.util.List;
import lombok.Data;

@Data
public class BroadcastUnitRequest {
    private String unit;              // Example: Alpha, Bravo, Medical
    private String content;
    private List<AttachmentRequest> attachments;
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
