package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.ChatRoom;

public interface IChatRoomService {
	
	List<ChatRoom> findAllChatRooms();
	Optional<ChatRoom> findChatRoomById(Long id);
	ChatRoom saveChatRoom(ChatRoom chatRoom);
	String deleteChatRoom(Long id);
	String updateChatRoom(ChatRoom chatRoomUpdated);

}
