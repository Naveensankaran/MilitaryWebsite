package com.military.app.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.dto.NotificationResponse;
import com.military.app.entity.User;
import com.military.app.service.NotificationService;
import com.military.app.service.UserService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService,
                                  UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    // ✅ ALL NOTIFICATIONS
    @GetMapping
    public List<NotificationResponse> getAllNotifications(Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        return notificationService.getAllNotifications(user.getId());
    }

    // ✅ UNREAD NOTIFICATIONS
    @GetMapping("/unread")
    public List<NotificationResponse> getUnreadNotifications(Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        return notificationService.getUnreadNotifications(user.getId());
    }
    
    @PutMapping("/{id}/read")
    public String markNotificationAsRead(
            @PathVariable Long id,
            Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());

        notificationService.markNotificationAsRead(id, user.getId());

        return "Notification marked as read";
    }
}
