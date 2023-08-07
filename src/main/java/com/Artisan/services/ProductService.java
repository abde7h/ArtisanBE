package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Artisan.entities.Product;
import com.Artisan.repository.ProductRepository;
import com.Artisan.services.interfaces.IProductService;

@Service

public class ProductService implements IProductService{

	ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {

		this.productRepository = productRepository;

	}

	@Override
	public List<Product> findAllProducts() {
		
		return productRepository.findAll();
		
	}

	@Override
	public Optional<Product> findProductById(Integer id) {
		
		return productRepository.findById(id);
		
	}

	@Override
	public Product saveProduct(Product product) {
		
		productRepository.save(product);
		return product;
		
	}

	@Override
	public String deleteProduct(Integer id) {
		
		if (productRepository.findById(id).isPresent()) {
			
			productRepository.deleteById(id);
			return "Producto eliminado correctamente.";
			
		}
		
		return "Error! El producto no existe";
		
	}

	@Override
	public String updateProduct(Product productUpdated) {
		
		int num = productUpdated.getProduct_id();
		
		if (productRepository.findById((Integer) num).isPresent()) {
			
			Product productToUpdate = new Product();
			productToUpdate.setProduct_id(productUpdated.getProduct_id());
			productToUpdate.setArtisan_id(productUpdated.getArtisan_id());
			productToUpdate.setName(productUpdated.getName());
			productToUpdate.setImage(productUpdated.getImage());
			productToUpdate.setDescription(productUpdated.getDescription());
			productToUpdate.setPrice(productUpdated.getPrice());
			productToUpdate.setCategory_id(productUpdated.getCategory_id());
			productToUpdate.setCreation_date(productUpdated.getCreation_date());
			productToUpdate.setSold(productUpdated.getSold());
			productToUpdate.setUser_id(productUpdated.getUser_id());
			productToUpdate.setBuy_date(productUpdated.getBuy_date());
			productToUpdate.setVisible(productUpdated.getVisible());
			productRepository.save(productToUpdate);
			return "User modificado";
			
		}
		
		return "Error al modificar el producto";
		
	}

}
