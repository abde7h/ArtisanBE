package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.Artisan.entities.Artisan;

public interface IArtisanService {
	
	List<Artisan> findAllArtisans();
	Optional<Artisan> findArtisanById(Integer id);
	Optional<Artisan> findArtisanByEmail(String email);
	Optional<Artisan> findArtisanByUsername(String username);
	ResponseEntity<Object> saveArtisan(Artisan artisanAdd);
	String deleteArtisan(Integer id);
	ResponseEntity<Object> updateArtisan(Artisan artisanUpdated);

}
