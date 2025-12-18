package com.military.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.military.app.entity.MessageRecipient;

public interface NotificationRepository extends JpaRepository<MessageRecipient, Long> {

    @Query("""
        SELECT r FROM MessageRecipient r
        WHERE r.receiverId = :userId
        ORDER BY r.id DESC
    """)
    List<MessageRecipient> findAllNotifications(Long userId);

    // ✅ UNREAD NOTIFICATIONS
    @Query("""
        SELECT r FROM MessageRecipient r
        WHERE r.receiverId = :userId AND r.readStatus = false
        ORDER BY r.id DESC
    """)
    List<MessageRecipient> findUnreadNotifications(Long userId);
}
