package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.Category;

public interface ICategoryService {
	
	List<Category> findAllCategories();
	Optional<Category> findCategoryById(Integer id);
	Category saveCategory(Category category);
	String deleteCategory(Integer id);
	String updateCategory(Category categoryUpdated);
	
}
