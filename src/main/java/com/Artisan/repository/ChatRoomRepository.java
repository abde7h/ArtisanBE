package com.Artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Artisan.entities.ChatRoom;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {}
