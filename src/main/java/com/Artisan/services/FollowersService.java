package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Artisan.entities.Followers;
import com.Artisan.repository.FollowersRepository;
import com.Artisan.services.interfaces.IFollowersService;

@Service

public class FollowersService implements IFollowersService{

	FollowersRepository followersRepository;

	public FollowersService(FollowersRepository followersRepository) {

		this.followersRepository = followersRepository;

	}

	@Override
	public List<Followers> findAllFollowers() {

		return followersRepository.findAll();

	}

	@Override
	public Optional<Followers> findFollowersById(Long id) {

		return followersRepository.findById(id);

	}

	@Override
	public Followers saveFollowers(Followers followers) {

		followersRepository.save(followers);
		return followers;
		
	}

	@Override
	public String deleteFollowers(Long id) {
		
		if (followersRepository.findById(id).isPresent()) {
			
			followersRepository.deleteById(id);
			return "Followers eliminado correctamente.";
			
		}
		
		return "Error! No existen Followers";
		
	}

	@Override
	public String updateFollowers(Followers followersUpdated) {
		
		int num = followersUpdated.getFollowing_id();
		
		if (followersRepository.findById((long) num).isPresent()) {
			
			Followers followersToUpdate = new Followers();
			followersToUpdate.setFollower_id(followersUpdated.getFollower_id());
			followersToUpdate.setFollowing_id(followersUpdated.getFollowing_id());
			followersRepository.save(followersToUpdate);
			return "Followers modificado";
			
		}
		
		return "Error al modificar Followers";
		
	}

}
