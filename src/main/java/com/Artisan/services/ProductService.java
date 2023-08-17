package com.Artisan.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Artisan.entities.Artisan;
import com.Artisan.entities.Likes;
import com.Artisan.entities.Product;
import com.Artisan.entities.DTOs.ArtisanDTONameSurname;
import com.Artisan.entities.DTOs.FeedDTO;
import com.Artisan.entities.DTOs.ProductProfileDTO;
import com.Artisan.repository.ArtisanRepository;
import com.Artisan.repository.LikesRepository;
import com.Artisan.repository.ProductRepository;
import com.Artisan.services.interfaces.IProductService;

@Service

public class ProductService implements IProductService {

	ProductRepository productRepository;
	LikesRepository likesRepository;
	ArtisanRepository artisanRepository;

	public ProductService(ProductRepository productRepository, LikesRepository likesRepository,
			ArtisanRepository artisanRepository) {

		this.productRepository = productRepository;
		this.likesRepository = likesRepository;
		this.artisanRepository = artisanRepository;
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
			return "Product modificado";

		}

		return "Error al modificar el producto";

	}

	public ResponseEntity<String> uploadPhoto(Integer productId, MultipartFile file) {

		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("File is empty");
			}
			
			String originalFilename = file.getOriginalFilename();
	        String newFilename = productId + "_" + originalFilename;

			// Get a reference to the resource directory & Create a path for the image file
			Path filePath = Paths.get("src/main/resources/static/images/product").resolve(newFilename);
			
			 if (Files.exists(filePath)) {
		            return ResponseEntity.badRequest().body("File with the same name already exists");
		        }
			 
			// Save the file
			Files.copy(file.getInputStream(), filePath);

			return ResponseEntity.ok("File uploaded successfully");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload file: " + e.getMessage());
		}
	}
	
	public List<String> getProductPhotoUrls(Integer productId) {
	    List<String> photoUrls = new ArrayList<>();
	    
	    // Define the base URL for accessing images
	    String baseUrl = "/images/product/";

	    // Get a reference to the resource directory
	    Resource resourceDir = new ClassPathResource("static/images/product");

	    try {
	        // Get a list of files in the directory
	        File[] files = resourceDir.getFile().listFiles();

	        if (files != null) {
	            for (File file : files) {
	                // Filter files based on productId and extension
	                String filename = file.getName();
	                if (filename.startsWith(productId + "_")) {
	                    photoUrls.add(baseUrl + filename);
	                }
	            }
	        }
	    } catch (IOException e) {
	        // Handle the exception
	    }

	    return photoUrls;
	}
	

	

	// DTO
	public ProductProfileDTO productProfileDTO(Integer productId) {
		Optional<Product> product = productRepository.findById(productId);

		if (product.isPresent()) {

			ProductProfileDTO productProfileDTO = new ProductProfileDTO();
			productProfileDTO.setName(product.get().getName());
			productProfileDTO.setImage(product.get().getImage());
			productProfileDTO.setDescription(product.get().getDescription());
			productProfileDTO.setPrice(product.get().getPrice());
			productProfileDTO.setCreation_date(product.get().getCreation_date());
			Stream<Likes> likesStream = likesRepository.findAll().stream()
					.filter(likes -> likes.getProduct_id() == productId);
			List<Likes> likesList = likesStream.collect(Collectors.toList());
			productProfileDTO.setLikesCount(likesList.size());
			return productProfileDTO;
		}
		return null;
	}

	// DTO
	public List<FeedDTO> feedDTO() {
		List<Product> productList = productRepository.findAll();
		return productList.stream().map(product -> {
			FeedDTO feedDTO = new FeedDTO();
			feedDTO.setId(product.getProduct_id());
			feedDTO.setImage(product.getImage());
			feedDTO.setName(product.getName());
			Optional<Artisan> artisan = artisanRepository.findById(product.getArtisan_id());
			artisan.ifPresent(value -> {
				ArtisanDTONameSurname artisanDTONameSurname = new ArtisanDTONameSurname();
				artisanDTONameSurname.setUsername(value.getUsername());
				artisanDTONameSurname.setImage(value.getImage());
				feedDTO.setArtisan(artisanDTONameSurname);
			});

			return feedDTO;
		}).collect(Collectors.toList());
	}

}
