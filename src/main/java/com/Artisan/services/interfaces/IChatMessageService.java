package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.ChatMessage;

public interface IChatMessageService {
	
	List<ChatMessage> findAllChatMessages();
	Optional<ChatMessage> findChatMessageById(Integer id);
	ChatMessage saveChatMessage(ChatMessage chatMessage);
	String deleteChatMessage(Integer id);
	String updateChatMessage(ChatMessage chatMessageUpdated);

}
