package com.military.app.service.impl;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.military.app.dto.AttachmentRequest;
import com.military.app.dto.MessageResponse;
import com.military.app.dto.SendMessageRequest;
import com.military.app.dto.SentMessageStatusDto;
import com.military.app.entity.Message;
import com.military.app.entity.MessageRecipient;
import com.military.app.entity.User;
import com.military.app.repository.MessageRecipientRepository;
import com.military.app.repository.MessageRepository;
import com.military.app.repository.UserRepository;
import com.military.app.service.AttachmentService;
import com.military.app.service.MessageService;
import com.military.app.util.AesEncryptor;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageRecipientRepository recipientRepository;

    @Autowired
    private AttachmentService attachmentService;
    

    // ---------------------------------------------------
    // SEND MESSAGE
    // ---------------------------------------------------
    @Override
    public Message sendMessage(SendMessageRequest request, Long senderId) {

        if (request.getReceiverIds() == null || request.getReceiverIds().isEmpty()) {
            throw new RuntimeException("Receiver list cannot be empty");
        }

        // ✅ Validate receivers
        List<User> receivers = userRepository.findAllById(request.getReceiverIds());

        if (receivers.size() != request.getReceiverIds().size()) {
            throw new RuntimeException("One or more receivers not found");
        }

        // Encrypt content
        String encryptedContent = AesEncryptor.encrypt(request.getContent());

        Message message = new Message();
        message.setSenderId(senderId);
        message.setContent(encryptedContent);
        message.setSentAt(LocalDateTime.now());

        message = messageRepository.save(message);

        for (User receiver : receivers) {
            MessageRecipient recipient = new MessageRecipient();
            recipient.setMessageId(message.getId());
            recipient.setReceiverId(receiver.getId());
            recipient.setReadStatus(false);
            recipientRepository.save(recipient);
        }

        if (request.getAttachments() != null) {
            for (AttachmentRequest att : request.getAttachments()) {
                attachmentService.saveAttachment(
                        message.getId(),
                        att.getFileName(),
                        att.getFilePath(),
                        att.getFileSize()
                );
            }
        }

        return message;
    }

    // ---------------------------------------------------
    // INBOX
    // ---------------------------------------------------
    @Override
    public List<Message> getInbox(Long userId) {
        return messageRepository.findInboxByUserId(userId);
    }

    // ---------------------------------------------------
    // SENT MESSAGES
    // ---------------------------------------------------
    @Override
    public List<Message> getSentMessages(Long userId) {
        return messageRepository.findSentByUserId(userId);
    
    
    }
    @Override
    public MessageResponse readMessage(Long messageId, Long userId) {

        // 1️⃣ Find recipient record
        MessageRecipient recipient = recipientRepository
                .findByMessageIdAndReceiverId(messageId, userId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // 2️⃣ Mark as read
        if (!recipient.isReadStatus()) {
            recipient.setReadStatus(true);
            recipientRepository.save(recipient);
        }

        // 3️⃣ Fetch message using messageId
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // 4️⃣ Prepare response DTO
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setSenderId(message.getSenderId());
        response.setContent(
                AesEncryptor.decrypt(message.getContent()) // 🔓 decrypt
        );
        response.setSentAt(message.getSentAt());
        response.setReadStatus(true);

        return response;
    }
    
    @Override
    public SentMessageStatusDto getSentMessageStatus(Long messageId) {

        long total = recipientRepository.countByMessageId(messageId);
        long read = recipientRepository
                        .countByMessageIdAndReadStatus(messageId, true);

        SentMessageStatusDto dto = new SentMessageStatusDto();
        dto.setMessageId(messageId);
        dto.setTotalReceivers((int) total);
        dto.setReadCount((int) read);

        return dto;
    }
    
    
    @Override
    public MessageResponse getMessageById(Long messageId, Long userId) {

        // 1️⃣ Fetch message
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        boolean isSender = message.getSenderId().equals(userId);

        // 2️⃣ If not sender, verify receiver
        MessageRecipient recipient = null;
        if (!isSender) {
            recipient = recipientRepository
                    .findByMessageIdAndReceiverId(messageId, userId)
                    .orElseThrow(() -> new RuntimeException("Unauthorized access"));
        }

        // 3️⃣ Prepare response
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setSenderId(message.getSenderId());
        response.setContent(AesEncryptor.decrypt(message.getContent()));
        response.setSentAt(message.getSentAt());

        // read status comes from recipient table
        response.setReadStatus(
                isSender ? false : recipient.isReadStatus()
        );

        return response;
    }

    @Override
    public void markAsRead(Long messageId, Long userId) {

        MessageRecipient recipient = recipientRepository
            .findByMessageIdAndReceiverId(messageId, userId)
            .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!recipient.isReadStatus()) {
            recipient.setReadStatus(true);
            recipientRepository.save(recipient);
        }
    }

    @Override
    public void deleteMessage(Long messageId, Long userId) {

        MessageRecipient recipient = recipientRepository
            .findByMessageIdAndReceiverId(messageId, userId)
            .orElseThrow(() -> new RuntimeException("Message not found"));

        recipientRepository.delete(recipient);
    }
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void broadcastMessage(Long senderId, String content, List<AttachmentRequest> attachments) {

        List<Long> receiverIds = userRepository.findAll().stream()
                .filter(User::isActive)
                .filter(u -> !u.getId().equals(senderId))
                .map(User::getId)
                .toList();

        if (receiverIds.isEmpty()) {
            throw new RuntimeException("No active users found");
        }

        SendMessageRequest request = new SendMessageRequest();
        request.setContent(content);
        request.setReceiverIds(receiverIds);
        request.setAttachments(attachments);

        sendMessage(request, senderId);
    }


    @Override
    public void broadcastByRank(Long senderId, String rank, String content, List<AttachmentRequest> attachments) {

        List<Long> receiverIds = userRepository.findByRankNameIgnoreCase(rank).stream()
                .filter(User::isActive)
                .filter(u -> !u.getId().equals(senderId))
                .map(User::getId)
                .toList();

        if (receiverIds.isEmpty()) {
            throw new RuntimeException("No officers found for rank: " + rank);
        }

        SendMessageRequest request = new SendMessageRequest();
        request.setContent(content);
        request.setReceiverIds(receiverIds);
        request.setAttachments(attachments);

        sendMessage(request, senderId);
    }

   

    @Override
    public void broadcastByUnit(Long senderId, String unit, String content, List<AttachmentRequest> attachments) {

        List<Long> receiverIds = userRepository.findByUnitIgnoreCase(unit).stream()
                .filter(User::isActive)
                .filter(u -> !u.getId().equals(senderId))
                .map(User::getId)
                .toList();

        if (receiverIds.isEmpty()) {
            throw new RuntimeException("No officers found in unit: " + unit);
        }

        SendMessageRequest request = new SendMessageRequest();
        request.setContent(content);
        request.setReceiverIds(receiverIds);
        request.setAttachments(attachments);

        sendMessage(request, senderId);
    }

}

