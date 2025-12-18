package com.military.app.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import com.military.app.entity.Attachment;
import com.military.app.service.AttachmentService;
import com.military.app.service.UserService;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final UserService userService;

    public AttachmentController(AttachmentService attachmentService,
                                UserService userService) {
        this.attachmentService = attachmentService;
        this.userService = userService;
    }

    // ✅ POST /api/attachments/upload
    @PostMapping("/upload")
    public ResponseEntity<Attachment> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("messageId") Long messageId,
            Authentication authentication) throws IOException {

        Attachment saved = attachmentService.uploadEncryptedFile(
                messageId,
                file
        );

        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downloadAttachment(
            @PathVariable Long id) throws IOException {

        byte[] fileData = attachmentService.downloadAndDecrypt(id);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"attachment_" + id + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileData.length)
                .body(resource);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttachment(@PathVariable Long id) {

        attachmentService.deleteAttachment(id);

        return ResponseEntity.ok("Attachment deleted successfully");
    }
}
