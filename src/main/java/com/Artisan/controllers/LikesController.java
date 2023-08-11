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

import com.Artisan.entities.Likes;
import com.Artisan.services.LikesService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "http://localhost:3000")
@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class LikesController {

	LikesService likesService;

	public LikesController(LikesService likesService) {

		this.likesService = likesService;

	}

	@GetMapping("/likes")
	public List<Likes> getLikes() {

		log.info("Request a http://localhost:PORT/1.0.0/likes (GET)");
		return likesService.findAllLikes();

	}
		 

	@GetMapping("/likes/{productId}")
   public ResponseEntity<List<Likes>> getLikesByProductId(@PathVariable Integer productId) {
		
        List<Likes> likes = likesService.findLikesByProduct_Id(productId);
        return (likes.isEmpty()) ? ResponseEntity.notFound().build()
       		: ResponseEntity.ok(likes);
        }
  

	@GetMapping("/likes/{userId}/{productId}")
	public ResponseEntity<List<Likes>> getLikesByUserIdAndProductId(@PathVariable Integer userId,
			@PathVariable Integer productId) {

		List<Likes> likes = likesService.findLikesByUser_IdAndProduct_Id(userId, productId);
		return (likes.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(likes);

	}

	@PostMapping("/likes/add")
	public ResponseEntity<Likes> saveLikes(@RequestBody Likes likes) {

		log.info("Request a http://localhost:PORT/1.0.0/likes/add (POST)");
		Likes savedLikes = likesService.saveLikes(likes);
		return (savedLikes != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedLikes)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/likes/delete/{userId}/{productId}")
	public ResponseEntity<String> deleteLikes(@PathVariable Integer userId, @PathVariable Integer productId) {
		likesService.deleteLikes(userId, productId);
		return ResponseEntity.ok("Seguidor eliminado correctamente.");
	}

}
