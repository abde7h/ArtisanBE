package com.Artisan.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

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
			return "User modificado";

		}

		return "Error al modificar el producto";

	}
	
	//DTO
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

	//DTO
	public List<FeedDTO> feedDTO() {
		List<Product> productList = productRepository.findAll();
		return productList.stream().map(product -> {
			FeedDTO feedDTO = new FeedDTO();
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
