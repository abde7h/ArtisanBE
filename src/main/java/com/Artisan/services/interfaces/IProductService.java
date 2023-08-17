package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Artisan.entities.Product;

public interface IProductService {
	
	List<Product> findAllProducts();
	Optional<Product> findProductById(Integer id);
	Product saveProduct(Product product);
	String deleteProduct(Integer id);
	String updateProduct(Product productUpdated);
	public ResponseEntity<String> uploadPhoto(Integer productId, MultipartFile file);

}
