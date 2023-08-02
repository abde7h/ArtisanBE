package com.Artisan.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.Artisan.entities.Likes;

import jakarta.transaction.Transactional;


public interface LikesRepository extends JpaRepository<Likes, Long> {
	
    @Transactional
    @Modifying
    @Query("DELETE FROM Likes l WHERE l.user_id = :userId AND l.product_id = :productId")
    void deleteByUser_idAndProduct_id(Integer userId, Integer productId);

    @Query("SELECT l FROM Likes l WHERE l.user_id = :userId AND l.product_id = :productId")
    List<Likes> findByUser_idAndProduct_id(Integer userId, Integer productId);
    
    @Query("SELECT l FROM Likes l WHERE  l.product_id = :productId")
    List<Likes> findByProduct_id(Integer productId);
}

