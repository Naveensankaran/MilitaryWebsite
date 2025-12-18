package com.military.app.service;

import java.util.List;
import com.military.app.dto.NotificationResponse;

public interface NotificationService {

    List<NotificationResponse> getAllNotifications(Long userId);

    // ✅ UNREAD
    List<NotificationResponse> getUnreadNotifications(Long userId);
    
    void markNotificationAsRead(Long notificationId, Long userId);
}
