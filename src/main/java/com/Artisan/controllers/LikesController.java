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

import com.Artisan.entities.Likes;
import com.Artisan.services.LikesService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class LikesController {

	LikesService likesService;

	public LikesController(LikesService likesService) {

		this.likesService = likesService;

	}

	@RequestMapping("/likes")
	public List<Likes> getLikes() {

		log.info("Request a http://localhost:PORT/1.0.0/likes (GET)");
		return likesService.findAllLikes();

	}

	@RequestMapping("/likes/{idProduct}")
	public Optional<Likes> findLikesById(@PathVariable Long idProduct) {
		log.info("Request a http://localhost:PORT/1.0.0/likes/" + idProduct + " (GET)");
		Optional<Likes> likes = likesService.findLikesById(idProduct);
		return likes;

	}

	@PostMapping("/likes/add")
	public ResponseEntity<Likes> saveLikes(@RequestBody Likes likes) {
		
		log.info("Request a http://localhost:PORT/1.0.0/likes/add (POST)");
		Likes savedLikes = likesService.saveLikes(likes);
		return (savedLikes != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedLikes)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

	@DeleteMapping("/product/likes/{idProduct}")
	public ResponseEntity<Object> deleteLikes(@PathVariable Long idProduct) {
		log.info("Request a http://localhost:PORT/1.0.0/likes/delete/" + idProduct + " (DELETE)");
		String result = likesService.deleteLikes(idProduct);
		return (result.equals("Likes eliminado correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();

	}

	@PatchMapping("/likes/update")
	public ResponseEntity<String> updateLikes(@RequestBody Likes likesUpdated) {
		
		log.info("Request a http://localhost:PORT/1.0.0/likes/update (PATCH)");
		Long productId = (long) likesUpdated.getProduct_id();
		Optional<Likes> existingProduct = likesService.findLikesById(productId);
		
		if (existingProduct.isPresent()) {
			
			likesService.updateLikes(likesUpdated);
			return ResponseEntity.ok("Likes modificado");
			
		} else {
			
			return ResponseEntity.notFound().build();
			
		}
	}

}
