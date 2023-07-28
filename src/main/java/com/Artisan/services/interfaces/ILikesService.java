package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.Likes;

public interface ILikesService {
	
	List<Likes> findAllLikes();
	Optional<Likes> findLikesById(Long id);
	Likes saveLikes(Likes likes);
	String deleteLikes(Long id);
	String updateLikes(Likes likesUpdated);

}
