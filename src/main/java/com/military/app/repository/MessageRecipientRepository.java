package com.military.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.military.app.entity.MessageRecipient;

public interface MessageRecipientRepository extends JpaRepository<MessageRecipient, Long> {
	  Optional<MessageRecipient> findByMessageIdAndReceiverId(
	            Long messageId,
	            Long receiverId
	    );
	  long countByMessageId(Long messageId);

	  long countByMessageIdAndReadStatus(Long messageId, boolean readStatus);
	  Optional<MessageRecipient> findByIdAndReceiverId(Long id, Long receiverId);
	  long countByReadStatusFalse();

	  @Query("""
			  SELECT COUNT(r)
			  FROM MessageRecipient r
			  JOIN Message m ON r.messageId = m.id
			  WHERE r.receiverId = :userId
			    AND m.senderId = :senderId
			    AND r.readStatus = false
			  """)
			  long countUnread(Long userId, Long senderId);

}
