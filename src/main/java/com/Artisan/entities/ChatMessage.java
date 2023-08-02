package com.Artisan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Chat_Message")

public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	Integer message_id;
	@Column(name = "room_id")
	Integer room_id;
	@Column(name = "user_id")
	Integer user_id;
	@Column(name = "artisan_id")
	Integer artisan_id;
	@Column(name = "timestamp")
	String timestamp;
	
}