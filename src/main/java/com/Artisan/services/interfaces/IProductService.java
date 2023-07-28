package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.Product;

public interface IProductService {
	
	List<Product> findAllProducts();
	Optional<Product> findProductById(Long id);
	Product saveProduct(Product product);
	String deleteProduct(Long id);
	String updateProduct(Product productUpdated);

}
