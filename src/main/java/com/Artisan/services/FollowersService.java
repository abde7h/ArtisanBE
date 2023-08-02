package com.Artisan.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Artisan.entities.Followers;
import com.Artisan.repository.FollowersRepository;
import com.Artisan.services.interfaces.IFollowersService;

@Service
public class FollowersService implements IFollowersService {

	FollowersRepository followersRepository;

	public FollowersService(FollowersRepository followersRepository) {

		this.followersRepository = followersRepository;

	}

	@Override
	public List<Followers> findAllFollowers() {
		return followersRepository.findAll();
	}
	
	@Override
	public List<Followers> findFollowersByFollower_Id(Integer followerId) {
        return followersRepository.findByFollower_id(followerId);
    }

	@Override
	public List<Followers> findFollowerByFollower_IdAndFollowing_Id(Integer followerId, Integer followingId) {
		
		return followersRepository.findByFollower_idAndFollowing_id(followerId, followingId);
	}

	@Override
	public Followers saveFollowers(Followers followers) {

		followersRepository.save(followers);
		return followers;

	}

	@Override
	public void deleteFollower(Integer followerId, Integer followingId) {
		followersRepository.deleteByFollower_idAndFollowing_id(followerId, followingId);
	}
}
