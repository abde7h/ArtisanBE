package com.Artisan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.ChatRoom;
import com.Artisan.services.ChatRoomService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class ChatRoomController {
	ChatRoomService chatRoomService;

	public ChatRoomController(ChatRoomService chatRoomService) {
		
		this.chatRoomService = chatRoomService;
		
	}

	@GetMapping("/chatRoom")
	public List<ChatRoom> getChatRoom() {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatRoom (GET)");
		return chatRoomService.findAllChatRooms();
		
	}

	@GetMapping("/chatRoom/{idChat}")
	public Optional<ChatRoom> findChatRoomById(@PathVariable Long idChat) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatRoom/" + idChat + " (GET)");
		Optional<ChatRoom> chatRoom = chatRoomService.findChatRoomById(idChat);
		return chatRoom;
		
	}

	@PostMapping("/chatRoom/add")
	public ResponseEntity<ChatRoom> saveChatRoom(@RequestBody ChatRoom chatRoom) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatRoom/add (POST)");
		ChatRoom savedChat_Room = chatRoomService.saveChatRoom(chatRoom);
		return (savedChat_Room != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedChat_Room)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/chatRoom/delete/{idChat}")
	public ResponseEntity<Object> deleteChatRoom(@PathVariable Long idChat) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatRoom/delete/" + idChat + " (DELETE)");
		String result = chatRoomService.deleteChatRoom(idChat);
		return (result.equals("Chat Room eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();
		
	}

	@PatchMapping("/chatRoom/update")
	public ResponseEntity<String> updateChatRoom(@RequestBody ChatRoom chatRoomUpdated) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatRoom/update (PATCH)");
		Long roomId = (long) chatRoomUpdated.getRoom_id();
		Optional<ChatRoom> existingProduct = chatRoomService.findChatRoomById(roomId);

		if (existingProduct.isPresent()) {
			
			chatRoomService.updateChatRoom(chatRoomUpdated);
			return ResponseEntity.ok("Chat_room modificado");
			
		} else {
			
			log.severe(chatRoomUpdated.toString());
			return ResponseEntity.notFound().build();
			
		}
	}

}