package com.military.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.military.app.dto.NotificationResponse;
import com.military.app.entity.Message;
import com.military.app.entity.MessageRecipient;
import com.military.app.exception.ResourceNotFoundException;
import com.military.app.repository.MessageRecipientRepository;
import com.military.app.repository.MessageRepository;
import com.military.app.repository.NotificationRepository;
import com.military.app.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MessageRepository messageRepository;
    private final MessageRecipientRepository recipientRepository;

    public NotificationServiceImpl(
            NotificationRepository notificationRepository,
            MessageRepository messageRepository,
            MessageRecipientRepository recipientRepository) {

        this.notificationRepository = notificationRepository;
        this.messageRepository = messageRepository;
        this.recipientRepository = recipientRepository;
    }

    // ---------------- ALL NOTIFICATIONS ----------------
    @Override
    public List<NotificationResponse> getAllNotifications(Long userId) {

        return notificationRepository.findAllNotifications(userId)
                .stream()
                .map(r -> {
                    Message m = messageRepository
                            .findById(r.getMessageId())
                            .orElse(null);

                    NotificationResponse dto = new NotificationResponse();
                    dto.setMessageId(r.getMessageId());
                    dto.setSenderId(m != null ? m.getSenderId() : null);
                    dto.setRead(r.isReadStatus());
                    dto.setSentAt(m != null ? m.getSentAt() : null);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ---------------- UNREAD NOTIFICATIONS ----------------
    @Override
    public List<NotificationResponse> getUnreadNotifications(Long userId) {

        return notificationRepository.findUnreadNotifications(userId)
                .stream()
                .map(r -> {
                    Message m = messageRepository
                            .findById(r.getMessageId())
                            .orElse(null);

                    NotificationResponse dto = new NotificationResponse();
                    dto.setMessageId(r.getMessageId());
                    dto.setSenderId(m != null ? m.getSenderId() : null);
                    dto.setRead(r.isReadStatus());
                    dto.setSentAt(m != null ? m.getSentAt() : null);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ---------------- MARK AS READ ----------------
    @Override
    public void markNotificationAsRead(Long notificationId, Long userId) {

        MessageRecipient recipient = recipientRepository
                .findByIdAndReceiverId(notificationId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found"));

        if (!recipient.isReadStatus()) {
            recipient.setReadStatus(true);
            recipientRepository.save(recipient);
        }
    }
}
