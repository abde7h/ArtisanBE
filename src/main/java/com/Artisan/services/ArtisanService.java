package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Artisan.entities.Artisan;
import com.Artisan.repository.ArtisanRepository;
import com.Artisan.services.interfaces.IArtisanService;

@Service

public class ArtisanService implements IArtisanService{

	ArtisanRepository artisanRepository;

	public ArtisanService(ArtisanRepository artisanRepository) {
		
		this.artisanRepository = artisanRepository;
		
	}
	
	@Override
	public List<Artisan> findAllArtisans() {
		
		return artisanRepository.findAll();
		
	}
	
	@Override
	public Optional<Artisan> findArtisanById(Long id) {
		
		return artisanRepository.findById(id);
		
	}

	public Artisan saveArtisan(Artisan artisan) throws DataIntegrityViolationException {
		
		if (artisanRepository.findByEmail(artisan.getEmail()).isPresent()) {
			
			throw new DataIntegrityViolationException("El correo electrónico ya está en uso.");
			
		}
		
		artisanRepository.save(artisan);
		return artisan;
		
	}
	
	@Override
	public String deleteArtisan(Long id) {
		
		if (artisanRepository.findById(id).isPresent()) {
			
			artisanRepository.deleteById(id);
			return "Artesano eliminado correctamente.";
			
		}
		
		return "Error! El artesano no existe";
		
	}
	
	@Override
	public String updateArtisan(Artisan artisanUpdated) {
		
		int num = artisanUpdated.getArtisan_id();
		
		if (artisanRepository.findById((long) num).isPresent()) {
			
			Artisan artisanToUpdate = new Artisan();
			artisanToUpdate.setArtisan_id(artisanToUpdate.getArtisan_id());
			artisanToUpdate.setName(artisanUpdated.getName());
			artisanToUpdate.setEmail(artisanUpdated.getEmail());
			artisanToUpdate.setPassword(artisanUpdated.getPassword());
			artisanToUpdate.setName(artisanUpdated.getName());
			artisanToUpdate.setSurnames(artisanUpdated.getSurnames());
			artisanToUpdate.setTelephone(artisanUpdated.getTelephone());
			artisanToUpdate.setDescription(artisanUpdated.getDescription());
			artisanToUpdate.setImage(artisanUpdated.getImage());
			artisanRepository.save(artisanToUpdate);
			return "Artesano  modificado";
			
		}
		
		return "Error al modificar el Artesano";
		
	}

}
