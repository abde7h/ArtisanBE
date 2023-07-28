package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Artisan.entities.Likes;
import com.Artisan.repository.LikesRepository;
import com.Artisan.services.interfaces.ILikesService;

@Service

public class LikesService implements ILikesService{

	LikesRepository likesRepository;

	public LikesService(LikesRepository likesRepository) {

		this.likesRepository = likesRepository;

	}

	@Override
	public List<Likes> findAllLikes() {

		return likesRepository.findAll();

	}

	@Override
	public Optional<Likes> findLikesById(Long id) {
		
		return likesRepository.findById(id);
		
	}

	@Override
	public Likes saveLikes(Likes likes) {
		
		likesRepository.save(likes);
		return likes;
		
	}

	@Override
	public String deleteLikes(Long id) {
		
		if (likesRepository.findById(id).isPresent()) {
			
			likesRepository.deleteById(id);
			return "Like eliminado correctamente.";
			
		}
		
		return "Error! No existen likes";
		
	}

	@Override
	public String updateLikes(Likes likesUpdated) {
		
		int num = likesUpdated.getProduct_id();
		
		if (likesRepository.findById((long) num).isPresent()) {
			
			Likes likesToUpdate = new Likes();
			likesToUpdate.setUser_id(likesUpdated.getUser_id());
			likesToUpdate.setProduct_id(likesUpdated.getProduct_id());
			likesRepository.save(likesToUpdate);
			return "likes modificado";
			
		}
		
		return "Error al modificar likes";
		
	}

}
