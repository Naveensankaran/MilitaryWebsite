package com.military.app.entity;

import jakarta.persistence.*;
//import lombok.*;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime sentAt = LocalDateTime.now();
//    @Column(name = "read_status")
//    private boolean readStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getSentAt() {
		return sentAt;
	}
	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
//	public boolean isReadStatus() {
//		return readStatus;
//	}
//	public void setReadStatus(boolean readStatus) {
//		this.readStatus = readStatus;
//	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    


}
