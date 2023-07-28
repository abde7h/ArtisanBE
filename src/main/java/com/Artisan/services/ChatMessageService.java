package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Artisan.entities.ChatMessage;
import com.Artisan.repository.ChatMessageRepository;
import com.Artisan.services.interfaces.IChatMessageService;

@Service

public class ChatMessageService implements IChatMessageService {

	ChatMessageRepository chatMessageRepository;

	public ChatMessageService(ChatMessageRepository chatMessageRepository) {

		this.chatMessageRepository = chatMessageRepository;

	}

	@Override
	public List<ChatMessage> findAllChatMessages() {

		return chatMessageRepository.findAll();
		
	}

	@Override
	public Optional<ChatMessage> findChatMessageById(Long id) {
		
		return chatMessageRepository.findById(id);
		
	}

	@Override
	public ChatMessage saveChatMessage(ChatMessage chatMessage) {
		
		chatMessageRepository.save(chatMessage);
		return chatMessage;
		
	}

	@Override
	public String deleteChatMessage(Long id) {
		
		if (chatMessageRepository.findById(id).isPresent()) {
			chatMessageRepository.deleteById(id);
			return "Chat_Room eliminado correctamente.";
		}
		
		return "Error! El Chat Message no existe";
		
	}

	@Override
	public String updateChatMessage(ChatMessage chatMessageUpdated) {
		
		int num = chatMessageUpdated.getMessage_id();
		
		if (chatMessageRepository.findById((long) num).isPresent()) {
			
			ChatMessage chatMessageToUpdate = new ChatMessage();
			chatMessageToUpdate.setMessage_id(chatMessageUpdated.getMessage_id());
			chatMessageToUpdate.setRoom_id(chatMessageUpdated.getRoom_id());
			chatMessageToUpdate.setUser_id(chatMessageUpdated.getUser_id());
			chatMessageToUpdate.setArtisan_id(chatMessageUpdated.getArtisan_id());
			chatMessageToUpdate.setTimestamp(chatMessageUpdated.getTimestamp());
			chatMessageRepository.save(chatMessageToUpdate);
			return "Chat_Message modificado";
			
		}
		
		return "Error al modificar el Chat_Message";
		
	}

}