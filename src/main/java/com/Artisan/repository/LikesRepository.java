package com.Artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Artisan.entities.Likes;


public interface LikesRepository extends JpaRepository<Likes, Long> {
	
}

