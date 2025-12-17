package com.military.app.service;

import java.util.List;

import com.military.app.dto.AttachmentRequest;
import com.military.app.dto.MessageResponse;
import com.military.app.dto.SendMessageRequest;
import com.military.app.dto.SentMessageStatusDto;
import com.military.app.entity.Message;

public interface MessageService {

    Message sendMessage(SendMessageRequest request);

    List<Message> getInbox(Long userId);

    List<Message> getSentMessages(Long userId);
    MessageResponse readMessage(Long messageId, Long userId);
    SentMessageStatusDto getSentMessageStatus(Long messageId);
    MessageResponse getMessageById(Long messageId, Long userId);
    void markAsRead(Long messageId, Long userId);
    void deleteMessage(Long messageId, Long userId);
    void broadcastMessage(SendMessageRequest request);
    void broadcastByRank(
            Long senderId,
            String rank,
            String content,
            List<AttachmentRequest> attachments
    );
    void broadcastByUnit(
            Long senderId,
            String unit,
            String content,
            List<AttachmentRequest> attachments
    );


}

