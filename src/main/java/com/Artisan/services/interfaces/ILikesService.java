package com.Artisan.services.interfaces;

import java.util.List;

import com.Artisan.entities.Likes;

public interface ILikesService {
	
	 List<Likes> findAllLikes();
	 public List<Likes> findLikesByProduct_Id(Integer productId);
//	 public Optional<Likes> findLikesById(Long id);
	 List<Likes> findLikesByUser_IdAndProduct_Id(Integer userId, Integer productId);
	 Likes saveLikes(Likes likes);
	 void deleteLikes(Integer userId, Integer productId);
	
}
