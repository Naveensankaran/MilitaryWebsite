package com.military.app.service;

import java.time.LocalDate;
import java.util.List;

import com.military.app.entity.AuditLog;

public interface AuditService {

    void logUserAction(Long userId, String action);

    void logMessageAction(Long userId, Long messageId, String action);

    List<AuditLog> getMessageAuditTrail();
    
    List<AuditLog> getUserActionLogs();
    
    List<AuditLog> getAuditByDate(LocalDate date);
}
