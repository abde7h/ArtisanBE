package com.Artisan.services;

import java.util.List;

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
	public List<Likes> findLikesByProduct_Id(Integer productId) {
		
        return likesRepository.findByProduct_id(productId);
        
    }
		
	

	@Override
	public List<Likes> findLikesByUser_IdAndProduct_Id(Integer userId, Integer productId) {
		
		return likesRepository.findByUser_idAndProduct_id(userId, productId);
		
	}

	@Override
	public Likes saveLikes(Likes likes) {

		likesRepository.save(likes);
		return likes;

	}

	@Override
	public void deleteLikes(Integer userId, Integer productId) {
		
		likesRepository.deleteByUser_idAndProduct_id(userId, productId);
		
	}
}
