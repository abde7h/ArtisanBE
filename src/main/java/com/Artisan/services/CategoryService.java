package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Artisan.entities.Category;
import com.Artisan.repository.CategoryRepository;
import com.Artisan.services.interfaces.ICategoryService;

@Service

public class CategoryService implements ICategoryService{

	CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		
		this.categoryRepository = categoryRepository;
		
	}

	@Override
	public List<Category> findAllCategories() {
		
		return categoryRepository.findAll();
		
	}
	
	@Override
	public Optional<Category> findCategoryById(Long id) {
		
		return categoryRepository.findById(id);
		
	}
	
	@Override
	public Category saveCategory(Category category) {
		
		categoryRepository.save(category);
		return category;
		
	}
	
	@Override
	public String deleteCategory(Long id) {
		
		if (categoryRepository.findById(id).isPresent()) {
			
			categoryRepository.deleteById(id);
			return "Categoria eliminada correctamente.";
			
		}
		
		return "Error! La categoria no existe";
		
	}
	
	@Override
	public String updateCategory(Category categoryUpdated) {
		
		int num = categoryUpdated.getId();
		
		if (categoryRepository.findById((long) num).isPresent()) {
			
			Category categoryToUpdate = new Category();
			categoryToUpdate.setId(categoryUpdated.getId());
			categoryToUpdate.setName(categoryUpdated.getName());
			categoryRepository.save(categoryToUpdate);
			return "Categoria modificada";
			
		}
		
		return "Error al modificar la categoria";
		
	}

}
