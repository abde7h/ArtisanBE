package com.Artisan.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Artisan.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
	
}

