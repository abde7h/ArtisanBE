package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.Category;

public interface ICategoryService {
	
	List<Category> findAllCategories();
	Optional<Category> findCategoryById(Long id);
	Category saveCategory(Category category);
	String deleteCategory(Long id);
	String updateCategory(Category categoryUpdated);
	
}
