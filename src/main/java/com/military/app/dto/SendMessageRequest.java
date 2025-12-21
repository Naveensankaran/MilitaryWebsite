package com.military.app.dto;

import java.util.List;

public class SendMessageRequest {

    private String content;
    private List<Long> receiverIds;
    private List<AttachmentRequest> attachments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
