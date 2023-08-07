package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.ChatRoom;

public interface IChatRoomService {
	
	List<ChatRoom> findAllChatRooms();
	Optional<ChatRoom> findChatRoomById(Integer id);
	ChatRoom saveChatRoom(ChatRoom chatRoom);
	String deleteChatRoom(Integer id);
	String updateChatRoom(ChatRoom chatRoomUpdated);

}
