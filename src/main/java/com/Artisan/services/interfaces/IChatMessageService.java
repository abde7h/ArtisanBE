package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.ChatMessage;

public interface IChatMessageService {
	
	List<ChatMessage> findAllChatMessages();
	Optional<ChatMessage> findChatMessageById(Long id);
	ChatMessage saveChatMessage(ChatMessage chatMessage);
	String deleteChatMessage(Long id);
	String updateChatMessage(ChatMessage chatMessageUpdated);

}
