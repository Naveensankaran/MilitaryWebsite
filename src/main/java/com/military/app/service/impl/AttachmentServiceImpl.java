package com.military.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.military.app.entity.Attachment;
import com.military.app.exception.ResourceNotFoundException;
import com.military.app.repository.AttachmentRepository;
import com.military.app.service.AttachmentService;
import com.military.app.util.AesEncryptor;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    private static final String UPLOAD_DIR = "uploads/";

    // ✅ EXISTING METHOD (unchanged)
    @Override
    public Attachment saveAttachment(
            Long messageId,
            String fileName,
            String filePath,
            long fileSize) {

        Attachment attachment = new Attachment();
        attachment.setMessageId(messageId);
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath);
        attachment.setFileSize(fileSize);

        return attachmentRepository.save(attachment);
    }

    // ✅ NEW METHOD – ENCRYPTED FILE UPLOAD
    @Override
    public Attachment uploadEncryptedFile(
            Long messageId,
            MultipartFile file) throws IOException {

        // 1️⃣ Read file bytes
        byte[] originalBytes = file.getBytes();

        // 2️⃣ Encrypt file content
        String encryptedContent =
                AesEncryptor.encrypt(new String(originalBytes));

        byte[] encryptedBytes = encryptedContent.getBytes();

        // 3️⃣ Ensure upload directory exists
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 4️⃣ Save encrypted file
        String filePath = UPLOAD_DIR
                + System.currentTimeMillis()
                + "_" + file.getOriginalFilename();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(encryptedBytes);
        }

        // 5️⃣ Save metadata to DB
        Attachment attachment = new Attachment();
        attachment.setMessageId(messageId);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFilePath(filePath);
        attachment.setFileSize(file.getSize());

        return attachmentRepository.save(attachment);
    }
    
    @Override
    public byte[] downloadAndDecrypt(Long attachmentId) throws IOException {

        // 1️⃣ Fetch attachment metadata
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attachment not found"));

        File file = new File(attachment.getFilePath());

        if (!file.exists()) {
            throw new ResourceNotFoundException("File not found on disk");
        }

        // 2️⃣ Read encrypted bytes
        byte[] encryptedBytes = Files.readAllBytes(file.toPath());

        // 3️⃣ Decrypt content
        String decryptedContent =
                AesEncryptor.decrypt(new String(encryptedBytes));

        // 4️⃣ Return original bytes
        return decryptedContent.getBytes();
    }
    
    
    @Override
    public void deleteAttachment(Long attachmentId) {

        // 1️⃣ Fetch attachment
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attachment not found"));

        // 2️⃣ Delete file from disk
        File file = new File(attachment.getFilePath());
        if (file.exists()) {
            file.delete();
        }

        // 3️⃣ Delete DB record
        attachmentRepository.deleteById(attachmentId);
    }
    
}
