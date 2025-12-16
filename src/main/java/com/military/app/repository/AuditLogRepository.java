package com.military.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.military.app.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
