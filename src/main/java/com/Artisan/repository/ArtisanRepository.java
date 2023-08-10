package com.Artisan.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Artisan.entities.Artisan;




public interface ArtisanRepository extends JpaRepository<Artisan, Integer> {
	 
	@Query("SELECT a FROM Artisan a WHERE a.email = :email")
	Optional<Artisan> findByEmail(String email);
	@Query("SELECT a FROM Artisan a WHERE a.username = :username")
	Optional<Artisan> findByUsername(String username);
	

	
}
