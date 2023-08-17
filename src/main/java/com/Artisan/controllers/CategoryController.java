package com.Artisan.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Artisan.entities.Category;
import com.Artisan.services.CategoryService;

import lombok.extern.java.Log;

@CrossOrigin(origins = "http://localhost:3000")
@Log
@RestController
@RequestMapping(value = "/1.0.0")

public class CategoryController {

	CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {

		this.categoryService = categoryService;

	}

	@GetMapping("/category") // Trae una lista con todos los elementos llamados Category
	public List<Category> getCategory() {
		
		log.info("Request a http://localhost:PORT/1.0.0/user (GET)");
		return categoryService.findAllCategories();

	}

	@GetMapping("/category/{idCategory}") // Trae una lista con todos los elementos llamados Category *Input ID Category*
	public Optional<Category> findCategoryById(@PathVariable Integer idCategory) {
		
		log.info("Request a http://localhost:PORT/1.0.0/category/" + idCategory + " (GET)");
		Optional<Category> category = categoryService.findCategoryById(idCategory);
		return category;

	}

	@PostMapping("/category/add") // Añade elemento Category a la BBDD
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
		
		log.info("Request a http://localhost:PORT/1.0.0/add (POST)");
		Category savedCategory = categoryService.saveCategory(category);
		return (savedCategory != null) ? ResponseEntity.status(HttpStatus.CREATED).body(savedCategory)
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
	}

	@DeleteMapping("/category/delete/{idCategory}") //Borra elemento Category de la BBDD *Input ID Category*
	public ResponseEntity<Object> deleteUser(@PathVariable Integer idCategory) {
		
		log.info("Request a http://localhost:PORT/1.0.0/user/delete/" + idCategory + " (DELETE)");
		String result = categoryService.deleteCategory(idCategory);	
		return (result.equals("Categoria eliminada correctamente.")) ? ResponseEntity.noContent().build()
				: ResponseEntity.notFound().build();
				
	}

	@PatchMapping("/category/update") //Actualiza elemento Category de la BBDD
	public ResponseEntity<String> updateCategory(@RequestBody Category categoryUpdated) {
		
		log.info("Request a http://localhost:PORT/1.0.0/user/update (PATCH)");
		Integer categoryId = categoryUpdated.getId();
		Optional<Category> existingCategory = categoryService.findCategoryById(categoryId);
		
		if (existingCategory.isPresent()) {
			
			categoryService.updateCategory(categoryUpdated);
			return ResponseEntity.ok("Categoria modificado");
			
		} else {
			
			return ResponseEntity.notFound().build();
			
		}
	}

}
