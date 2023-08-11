package com.Artisan.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.Followers;
import com.Artisan.services.FollowersService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "http://localhost:3000")
@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class FollowersController {

	FollowersService followersService;

	public FollowersController(FollowersService followersService) {

		this.followersService = followersService;

	}

	@GetMapping("/followers")
	public List<Followers> getFollowers() {
		log.info("Request a http://localhost:PORT/1.0.0/followers (GET)");
		return followersService.findAllFollowers();
	}

	@GetMapping("/followers/{followerId}")
	public ResponseEntity<List<Followers>> getFollowersByFollower_Id(@PathVariable Integer followerId) {

		List<Followers> followers = followersService.findFollowersByFollower_Id(followerId);
		return (followers.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(followers);
	}

	@GetMapping("/followers/{followerId}/{followingId}")
	public ResponseEntity<List<Followers>> getFollowerByFollowerIdAndFollowingId(@PathVariable Integer followerId,
			@PathVariable Integer followingId) {

		List<Followers> followers = followersService.findFollowerByFollower_IdAndFollowing_Id(followerId, followingId);
		return (followers.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(followers);

	}

	@PostMapping("/followers/add")
	public ResponseEntity<Followers> saveFollowers(@RequestBody Followers followers) {

		log.info("Request a http://localhost:PORT/1.0.0/followers/add (POST)");
		Followers savedFollowers = followersService.saveFollowers(followers);
		return (savedFollowers != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedFollowers)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/followers/delete/{followerId}/{followingId}")
	public ResponseEntity<String> deleteFollower(@PathVariable Integer followerId, @PathVariable Integer followingId) {
		followersService.deleteFollower(followerId, followingId);
		return ResponseEntity.ok("Seguidor eliminado correctamente.");
	}

}
