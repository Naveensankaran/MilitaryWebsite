package com.military.app.service.impl;

import com.military.app.entity.AuditLog;
import com.military.app.repository.AuditLogRepository;
import com.military.app.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void logAction(Long userId, String action) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setActionTime(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}
