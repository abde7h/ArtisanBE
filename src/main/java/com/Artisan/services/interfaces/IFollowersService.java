package com.Artisan.services.interfaces;

import java.util.List;

import com.Artisan.entities.Followers;



public interface IFollowersService {
	
	List<Followers> findAllFollowers();
	List<Followers> findFollowersByFollower_Id(Integer followerId);
	Followers saveFollowers(Followers followers);
	void deleteFollower(Integer followerId, Integer followingId);
	List<Followers> findFollowerByFollower_IdAndFollowing_Id(Integer followerId, Integer followingId);
}
