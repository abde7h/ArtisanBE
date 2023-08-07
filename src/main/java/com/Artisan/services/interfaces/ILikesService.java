package com.Artisan.services.interfaces;

import java.util.List;

import com.Artisan.entities.Likes;

public interface ILikesService {
	
	 List<Likes> findAllLikes();
	 List<Likes> findLikesByProduct_Id(Integer productId);
	 List<Likes> findLikesByUser_IdAndProduct_Id(Integer userId, Integer productId);
	 Likes saveLikes(Likes likes);
	 void deleteLikes(Integer userId, Integer productId);
	
}
