package com.Artisan.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Artisan.entities.Artisan;
import com.Artisan.entities.User;


public interface ArtisanRepository extends JpaRepository<Artisan, Long> {
	Optional<Artisan> findByEmail(String email);
}
