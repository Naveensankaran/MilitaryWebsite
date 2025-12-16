package com.military.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.military.app.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
