package com.military.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.military.app.dto.MessageResponse;
import com.military.app.dto.SendMessageRequest;
import com.military.app.dto.SentMessageStatusDto;
import com.military.app.entity.Message;
import com.military.app.entity.User;
import com.military.app.service.MessageService;
import com.military.app.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // SEND MESSAGE
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest request) {
        Message msg = messageService.sendMessage(request);
        return ResponseEntity.ok(msg);
    }

    // INBOX
    @GetMapping("/inbox/{userId}")
    public ResponseEntity<List<Message>> getInbox(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getInbox(userId));
    }

    // SENT ITEMS
    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<Message>> getSentMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getSentMessages(userId));
    }
    @GetMapping("/{messageId}/read/{userId}")
    public ResponseEntity<MessageResponse> readMessage(
            @PathVariable Long messageId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                messageService.readMessage(messageId, userId)
        );

    }
    
    @GetMapping("/sent/{messageId}/status")
    public ResponseEntity<SentMessageStatusDto> getSentMessageStatus(
            @PathVariable Long messageId) {

        return ResponseEntity.ok(
                messageService.getSentMessageStatus(messageId)
        );
    }
    private final UserService userService;

    public MessageController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponse> getMessage(
            @PathVariable Long messageId,
            Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());

        return ResponseEntity.ok(
                messageService.getMessageById(messageId, user.getId())
        );
    }
    
    @PutMapping("/{messageId}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long messageId,
            Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        messageService.markAsRead(messageId, user.getId());

        return ResponseEntity.ok("Message marked as read");
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(
            @PathVariable Long messageId,
            Authentication authentication) {

        User user = userService.findByUsername(authentication.getName());
        messageService.deleteMessage(messageId, user.getId());

        return ResponseEntity.ok("Message deleted successfully");
    }


}
