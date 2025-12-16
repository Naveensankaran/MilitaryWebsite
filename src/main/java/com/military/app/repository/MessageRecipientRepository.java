package com.military.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.military.app.entity.MessageRecipient;

public interface MessageRecipientRepository extends JpaRepository<MessageRecipient, Long> {
	  Optional<MessageRecipient> findByMessageIdAndReceiverId(
	            Long messageId,
	            Long receiverId
	    );
	  long countByMessageId(Long messageId);

	  long countByMessageIdAndReadStatus(Long messageId, boolean readStatus);

}
