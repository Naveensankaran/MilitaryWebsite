package com.military.app.service;

import com.military.app.entity.Attachment;

public interface AttachmentService {
    Attachment saveAttachment(Long messageId, String fileName, String filePath, long fileSize);
}
