package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.Artisan;

public interface IArtisanService {
	
	List<Artisan> findAllArtisans();
	Optional<Artisan> findArtisanById(Long id);
	Artisan saveArtisan(Artisan artisan);
	String deleteArtisan(Long id);
	String updateArtisan(Artisan artisanUpdated);

}
