package com.Artisan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.ChatMessage;
import com.Artisan.services.ChatMessageService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "http://localhost:3000")
@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class ChatMessageController {
	
	ChatMessageService chatMessageService;

	public ChatMessageController(ChatMessageService chatMessageService) {
		
		this.chatMessageService = chatMessageService;
		
	}

	@GetMapping("/chatMessage") // Trae una lista con todos los elementos llamados Chat Message
	public List<ChatMessage> getChatMessage() {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatMessage (GET)");
		return chatMessageService.findAllChatMessages();
		
	}

	@GetMapping("/chatMessage/{idChat}") // Trae elemento chatMessage con una determinada id *Input ID Chat*
	public Optional<ChatMessage> findChatMessageById(@PathVariable Integer idChat) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatMessage/" + idChat + " (GET)");
		Optional<ChatMessage> chatMessage = chatMessageService.findChatMessageById(idChat);
		return chatMessage;
		
	}

	@PostMapping("/chatMessage/add") // Añade un nuevo elemento Chat Message a la BBDD
	public ResponseEntity<ChatMessage> saveChatMessage(@RequestBody ChatMessage chatMessage) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatMessage/add (POST)");
		ChatMessage savedChatMessage = chatMessageService.saveChatMessage(chatMessage);
		return (savedChatMessage != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedChatMessage)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/chatMessage/delete/{idChat}") // Borra un elemento Chat Message de la BBDD con una determinada id *Input ID Chat Message*  
	public ResponseEntity<Object> deleteChatMessage(@PathVariable Integer idChat) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatMessage/delete/" + idChat + " (DELETE)");
		String result = chatMessageService.deleteChatMessage(idChat);
		return (result.equals("Chat Message eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();

	}

	@PatchMapping("/chatMessage/update") // Actualiza un elemento Chat Message en la BBDD
	public ResponseEntity<String> updateChatMessage(@RequestBody ChatMessage chatMessageUpdated) {
		
		log.info("Request a http://localhost:PORT/1.0.0/chatMessage/update (PATCH)");
		Integer messageId = chatMessageUpdated.getMessage_id();
		Optional<ChatMessage> existingProduct = chatMessageService.findChatMessageById(messageId);

		if (existingProduct.isPresent()) {
			
			chatMessageService.updateChatMessage(chatMessageUpdated);
			return ResponseEntity.ok("Chat message modificado");
			
		} else {
			
			log.severe(chatMessageUpdated.toString());
			return ResponseEntity.notFound().build();
			
		}
	}
}