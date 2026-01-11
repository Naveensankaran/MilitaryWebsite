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
    
    @Query("""
    	    SELECT m, r, u
    	    FROM Message m
    	    JOIN MessageRecipient r ON m.id = r.messageId
    	    JOIN User u ON m.senderId = u.id
    	    WHERE r.receiverId = :userId
    	""")
    	List<Object[]> findInboxDetailed(Long userId);
    	
    	@Query("""
    			SELECT m
    			FROM Message m
    			JOIN MessageRecipient r ON m.id = r.messageId
    			WHERE 
    			   (m.senderId = :loggedUserId AND r.receiverId = :otherUserId)
    			OR (m.senderId = :otherUserId AND r.receiverId = :loggedUserId)
    			ORDER BY m.sentAt
    			""")
    			List<Message> findConversation(
    			        Long loggedUserId,
    			        Long otherUserId
    			);
    	
    	 @Query("""
    		        SELECT m, u, r.readStatus
    		        FROM Message m
    		        JOIN User u ON u.id = m.senderId
    		        LEFT JOIN MessageRecipient r
    		            ON r.messageId = m.id AND r.receiverId = :loggedUserId
    		        WHERE
    		          (m.senderId = :loggedUserId AND r.receiverId = :otherUserId)
    		          OR
    		          (m.senderId = :otherUserId AND r.receiverId = :loggedUserId)
    		        ORDER BY m.sentAt
    		    """)
    		    List<Object[]> findConversationFull(
    		            Long loggedUserId,
    		            Long otherUserId
    		    );
    		    
    		    
    			
    			
    			@Query("""
    					SELECT m
    					FROM Message m
    					JOIN MessageRecipient r ON m.id = r.messageId
    					WHERE m.senderId = :senderId
    					  AND r.receiverId = :receiverId
    					ORDER BY m.sentAt DESC
    					""")
    					List<Message> findLastMessage(
    					        Long senderId,
    					        Long receiverId
    					);


    			@Query("""
    			        SELECT
    			            u.id,
    			            u.username,
    			            u.role.name,
    			            u.rankName,
    			            u.unit,
    			            MAX(m.sentAt),
    			            SUM(CASE WHEN r.readStatus = false THEN 1 ELSE 0 END),
    			            MAX(m.content)
    			        FROM Message m
    			        JOIN MessageRecipient r ON m.id = r.messageId
    			        JOIN User u ON u.id = m.senderId
    			        WHERE r.receiverId = :userId
    			        GROUP BY u.id, u.username, u.role.name, u.rankName, u.unit
    			        ORDER BY MAX(m.sentAt) DESC
    			    """)
    			    List<Object[]> findChatList(Long userId);		
    			
    			
}


