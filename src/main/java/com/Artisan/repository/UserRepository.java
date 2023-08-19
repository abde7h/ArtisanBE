package com.Artisan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Artisan.entities.User;
import com.Artisan.entities.DTOs.FollowersDTO;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    List<User> findByEmailAndPassword(String email, String password);
    
    @Query("SELECT new com.Artisan.entities.DTOs.FollowersDTO(a.username) " +
    	       "FROM Artisan a " +
    	       "INNER JOIN Followers f ON a.artisan_id = f.follower_id " +
    	       "INNER JOIN User u ON u.user_id = f.following_id " +
    	       "WHERE u.username = ?1")
    	List<FollowersDTO> findArtisansFollowedByUser(String username);
}
