package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Artisan.entities.ChatRoom;
import com.Artisan.repository.ChatRoomRepository;
import com.Artisan.services.interfaces.IChatRoomService;

@Service

public class ChatRoomService implements IChatRoomService{
	
	ChatRoomRepository chatRoomRepository;

	public ChatRoomService(ChatRoomRepository chatRoomRepository) {
		
		this.chatRoomRepository = chatRoomRepository;
		
	}

	@Override
	public List<ChatRoom> findAllChatRooms() {
		
		return chatRoomRepository.findAll();
		
	}

	@Override
	public Optional<ChatRoom> findChatRoomById(Integer id) {
		
		return chatRoomRepository.findById(id);
		
	}

	@Override
	public ChatRoom saveChatRoom(ChatRoom chatRoom) {
		
		chatRoomRepository.save(chatRoom);
		return chatRoom;
		
	}

	@Override
	public String deleteChatRoom(Integer id) {
		
		if (chatRoomRepository.findById(id).isPresent()) {
			
			chatRoomRepository.deleteById(id);
			return "Chat Room eliminado correctamente.";
			
		}
		
		return "Error! La Chat Room no existe";
		
	}

	@Override
	public String updateChatRoom(ChatRoom chatRoomUpdated) {
		
		int num = chatRoomUpdated.getProduct_id();
		
		if (chatRoomRepository.findById((Integer) num).isPresent()) {
			
			ChatRoom chatRoomToUpdate = new ChatRoom();
			chatRoomToUpdate.setRoom_id(chatRoomUpdated.getRoom_id());
			chatRoomToUpdate.setProduct_id(chatRoomUpdated.getProduct_id());
			chatRoomRepository.save(chatRoomToUpdate);
			return "Chat Room modificada";
			
		}
		
		return "Error al modificar la Chat Room";
		
	}

}
