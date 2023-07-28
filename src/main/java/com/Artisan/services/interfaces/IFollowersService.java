package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.Followers;

public interface IFollowersService {
	
	List<Followers> findAllFollowers();
	Optional<Followers> findFollowersById(Long id);
	Followers saveFollowers(Followers followers);
	String deleteFollowers(Long id);
	String updateFollowers(Followers followersUpdated);

}
