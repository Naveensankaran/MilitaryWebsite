package com.military.app.service;

import com.military.app.entity.AuditLog;

public interface AuditService {
    //void saveLog(AuditLog log);
    void logAction(Long userId, String action);
}
