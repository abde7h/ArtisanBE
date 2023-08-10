package com.Artisan.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.Artisan.entities.Followers;

import jakarta.transaction.Transactional;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {
	
 

    @Transactional
    @Modifying
    @Query("DELETE FROM Followers f WHERE f.follower_id = :followerId AND f.following_id = :followingId")
    void deleteByFollower_idAndFollowing_id(Integer followerId, Integer followingId);

    @Query("SELECT f FROM Followers f WHERE f.follower_id = :followerId AND f.following_id = :followingId")
    List<Followers> findByFollower_idAndFollowing_id(Integer followerId, Integer followingId);
    
    @Query("SELECT f FROM Followers f WHERE f.follower_id = :followerId")
    List<Followers> findByFollower_id(Integer followerId);
    
    
}


