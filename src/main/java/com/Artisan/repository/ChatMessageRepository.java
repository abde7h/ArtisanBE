package com.Artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Artisan.entities.ChatMessage;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {}
