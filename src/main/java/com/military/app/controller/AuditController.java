package com.military.app.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.entity.AuditLog;
import com.military.app.service.AuditService;

@RestController
	@RequestMapping("/api/audit")
	public class AuditController {

	    private final AuditService auditService;

	    public AuditController(AuditService auditService) {
	        this.auditService = auditService;
	    }

	    // ✅ GET /api/audit/messages
	    @GetMapping("/messages")
	    public List<AuditLog> getMessageAuditTrail() {
	        return auditService.getMessageAuditTrail();
	    }
	    
	    @GetMapping("/user-actions")
	    public List<AuditLog> getUserActions() {
	        return auditService.getUserActionLogs();
	    }
	    
	    @GetMapping("/{date}")
	    public List<AuditLog> getAuditByDate(
	            @PathVariable
	            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	            LocalDate date) {

	        return auditService.getAuditByDate(date);
	    }
	}



