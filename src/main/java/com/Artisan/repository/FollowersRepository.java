package com.Artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Artisan.entities.Followers;


public interface FollowersRepository extends JpaRepository<Followers, Long> {
	
}


