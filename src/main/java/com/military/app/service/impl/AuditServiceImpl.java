package com.military.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.military.app.entity.AuditLog;
import com.military.app.repository.AuditLogRepository;
import com.military.app.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // 🔐 Login / Logout / Password change
    @Override
    public void logUserAction(Long userId, String action) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setActionTime(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    // ✉ Message-related actions
    @Override
    public void logMessageAction(Long userId, Long messageId, String action) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setMessageId(messageId);
        log.setAction(action);
        log.setActionTime(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    // 📜 GET /api/audit/messages
    @Override
    public List<AuditLog> getMessageAuditTrail() {
        return auditLogRepository.findByMessageIdIsNotNull();
    }
    
    @Override
    public List<AuditLog> getUserActionLogs() {
        return auditLogRepository.findByMessageIdIsNull();
    }
    
    @Override
    public List<AuditLog> getAuditByDate(LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return auditLogRepository.findByActionTimeBetween(
                startOfDay,
                endOfDay
        );
    }
}
