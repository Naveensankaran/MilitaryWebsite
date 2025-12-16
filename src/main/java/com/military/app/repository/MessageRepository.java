package com.military.app.repository;

import com.military.app.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m " +
           "JOIN MessageRecipient r ON m.id = r.messageId " +
           "WHERE r.receiverId = :userId")
    List<Message> findInboxByUserId(Long userId);

    @Query("SELECT m FROM Message m WHERE m.senderId = :userId")
    List<Message> findSentByUserId(Long userId);
}
