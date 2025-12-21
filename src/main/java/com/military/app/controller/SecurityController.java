package com.military.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.entity.AuditLog;
import com.military.app.entity.LoginAttempt;
import com.military.app.repository.AuditLogRepository;
import com.military.app.repository.LoginAttemptRepository;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final AuditLogRepository auditLogRepository;
    private final LoginAttemptRepository loginAttemptRepository;

    // ✅ Constructor Injection (BEST PRACTICE)
    public SecurityController(AuditLogRepository auditLogRepository,
                              LoginAttemptRepository loginAttemptRepository) {
        this.auditLogRepository = auditLogRepository;
        this.loginAttemptRepository = loginAttemptRepository;
    }

    // ✅ GET /api/security/logs
    @GetMapping("/logs")
    public List<AuditLog> getAllSecurityLogs() {
        return auditLogRepository.findAll();
    }

    // ✅ GET /api/security/login-attempts
    @GetMapping("/login-attempts")
    public List<LoginAttempt> getLoginAttempts() {
        return loginAttemptRepository.findAll();
    }
    
    
}
