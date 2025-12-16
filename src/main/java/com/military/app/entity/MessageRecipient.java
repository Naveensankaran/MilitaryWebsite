package com.military.app.entity;

import jakarta.persistence.*;
//import lombok.*;

@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class MessageRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long messageId;

    private Long receiverId;

    
    @Column(name = "read_status")
    private boolean readStatus=false;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getMessageId() {
		return messageId;
	}


	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}


	public Long getReceiverId() {
		return receiverId;
	}


	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}


	public boolean isReadStatus() {
		return readStatus;
	}


	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}


	public MessageRecipient() {
		super();
		// TODO Auto-generated constructor stub
	}

    

}
