package com.military.app.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.military.app.dto.AdminDashboardResponse;
import com.military.app.entity.AuditLog;
import com.military.app.entity.User;
import com.military.app.exception.ResourceNotFoundException;
import com.military.app.repository.AttachmentRepository;
import com.military.app.repository.AuditLogRepository;
import com.military.app.repository.MessageRecipientRepository;
import com.military.app.repository.MessageRepository;
import com.military.app.repository.UserRepository;
import com.military.app.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageRecipientRepository recipientRepository;
    private final AttachmentRepository attachmentRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminServiceImpl(
            UserRepository userRepository,
            MessageRepository messageRepository,
            MessageRecipientRepository recipientRepository,
            AttachmentRepository attachmentRepository,
            AuditLogRepository auditLogRepository) {

        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.recipientRepository = recipientRepository;
        this.attachmentRepository = attachmentRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AdminDashboardResponse getDashboardData() {

        AdminDashboardResponse dto = new AdminDashboardResponse();

        dto.setTotalUsers(userRepository.count());
        dto.setActiveUsers(userRepository.countByActiveTrue());

        dto.setTotalMessages(messageRepository.count());
        dto.setUnreadMessages(recipientRepository.countByReadStatusFalse());

        dto.setTotalAttachments(attachmentRepository.count());
        dto.setTotalAuditLogs(auditLogRepository.count());

        return dto;
    }
    
    @Override
    public void enableUser(Long userId, String adminUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setActive(true);
        userRepository.save(user);

        // 🔐 AUDIT LOG
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction("USER ENABLED by ADMIN: " + adminUsername);
        log.setActionTime(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    @Override
    public void disableUser(Long userId, String adminUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setActive(false);
        userRepository.save(user);

        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction("USER DISABLED by ADMIN: " + adminUsername);
        log.setActionTime(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}
