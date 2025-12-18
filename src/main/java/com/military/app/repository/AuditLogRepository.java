package com.military.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.military.app.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByMessageIdIsNotNull();
    List<AuditLog> findByMessageIdIsNull();
    List<AuditLog> findByActionTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );
    
    
}
