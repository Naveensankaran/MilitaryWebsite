package com.military.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.military.app.entity.Attachment;

public interface AttachmentService {
    Attachment saveAttachment(Long messageId, String fileName, String filePath, long fileSize);
    Attachment uploadEncryptedFile(Long messageId,
            MultipartFile file) throws IOException;
    byte[] downloadAndDecrypt(Long attachmentId) throws IOException;
    void deleteAttachment(Long attachmentId);
    
}
