package com.Artisan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.Followers;
import com.Artisan.services.FollowersService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class FollowersController {

	FollowersService followersService;

	public FollowersController(FollowersService followersService) {

		this.followersService = followersService;

	}

	@RequestMapping("/followers")
	public List<Followers> getFollowers() {

		log.info("Request a http://localhost:PORT/1.0.0/followers (GET)");
		return followersService.findAllFollowers();

	}

	@RequestMapping("/followers/{idFollowing}")
	public Optional<Followers> findFollowersById(@PathVariable Long idFollowing) {
		
		log.info("Request a http://localhost:PORT/1.0.0/likes/" + idFollowing + " (GET)");
		Optional<Followers> followers = followersService.findFollowersById(idFollowing);
		return followers;

	}

	@PostMapping("/followers/add")
	public ResponseEntity<Followers> saveFollowers(@RequestBody Followers followers) {
		
		log.info("Request a http://localhost:PORT/1.0.0/likes/add (POST)");
		Followers savedFollowers = followersService.saveFollowers(followers);
		return (savedFollowers != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedFollowers)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/followers/delete/{idProduct}")
	public ResponseEntity<Object> deleteLikes(@PathVariable Long idFollowing) {
		
		log.info("Request a http://localhost:PORT/1.0.0/likes/delete/" + idFollowing + " (DELETE)");
		String result = followersService.deleteFollowers(idFollowing);
		return (result.equals("Followers eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();

	}

	@PatchMapping("/followers/update")
	public ResponseEntity<String> updateFollowers(@RequestBody Followers followersUpdated) {
		
		log.info("Request a http://localhost:PORT/1.0.0/likes/update (PATCH)");
		Long followingId = (long) followersUpdated.getFollowing_id();
		Optional<Followers> existingProduct = followersService.findFollowersById(followingId);
		
		if (existingProduct.isPresent()) {
			
			followersService.updateFollowers(followersUpdated);
			return ResponseEntity.ok("Followers modificado");
			
		} else {
			
			return ResponseEntity.notFound().build();
			
		}
	}

}
