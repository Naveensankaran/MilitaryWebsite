package com.military.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.military.app.entity.Attachment;
import com.military.app.repository.AttachmentRepository;
import com.military.app.service.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment saveAttachment(Long messageId, String fileName, String filePath, long fileSize) {
        Attachment attachment = new Attachment();

        attachment.setMessageId(messageId);
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath);
        attachment.setFileSize(fileSize);

        return attachmentRepository.save(attachment);
    }
}
