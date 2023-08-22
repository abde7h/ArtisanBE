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

import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Artisan.entities.Artisan;
import com.Artisan.entities.Followers;
import com.Artisan.entities.Product;
import com.Artisan.entities.DTOs.ArtisanDTO;
import com.Artisan.entities.DTOs.ArtisanProfileDTO;
import com.Artisan.helpers.EmailValidatorArtisan;
import com.Artisan.repository.ArtisanRepository;
import com.Artisan.repository.FollowersRepository;
import com.Artisan.repository.ProductRepository;
import com.Artisan.services.interfaces.IArtisanService;

@Service

public class ArtisanService implements IArtisanService {

	ArtisanRepository artisanRepository;
	ProductRepository productRepository;
	FollowersRepository followersRepository;
	EmailValidatorArtisan emailValidator = new EmailValidatorArtisan(this);

	public ArtisanService(ArtisanRepository artisanRepository, ProductRepository productRepository,
			FollowersRepository followersRepository) {
		this.artisanRepository = artisanRepository;
		this.productRepository = productRepository;
		this.followersRepository = followersRepository;
	}

	@Override
	public List<Artisan> findAllArtisans() {

		return artisanRepository.findAll();

	}

	@Override
	public Optional<Artisan> findArtisanById(Integer id) {

		return artisanRepository.findById(id);

	}

	@Override
	public Optional<Artisan> findArtisanByEmail(String email) {

		return artisanRepository.findByEmail(email);

	}
	
	@Override
	public Optional<Artisan> findArtisanByUsername(String username) {

		return artisanRepository.findByUsername(username);

	}


	@Override
	public ResponseEntity<Object> saveArtisan(Artisan artisanAdd) {

		if (emailValidator.checkValidAndExistEmail(artisanAdd.getEmail())) {
			artisanRepository.save(artisanAdd);
			artisanAdd.setImage("C:\\Users\\Tarda\\Desktop\\ArtisanBE\\target\\classes\\static\\images\\artisans\\" + artisanAdd.getArtisan_id() + "_Artisan");
			artisanRepository.save(artisanAdd);
			
			return ResponseEntity.ok(artisanAdd);

		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el correo ya existe o no es válido");

		}
	}

	@Override
	public String deleteArtisan(Integer id) {

		if (artisanRepository.findById(id).isPresent()) {

			artisanRepository.deleteById(id);
			return "Artesano eliminado correctamente.";

		}

		return "Error! El artesano no existe";

	}

	@Override
	public ResponseEntity<Object> updateArtisan(Artisan artisanUpdated) {

		Optional<Artisan> optionalArtisan = null;
		if (artisanUpdated.getArtisan_id() != null) {

			optionalArtisan = artisanRepository.findById(artisanUpdated.getArtisan_id());

			if (artisanUpdated.getEmail().equals(optionalArtisan.get().getEmail())) {

				artisanRepository.save(artisanUpdated);
				return ResponseEntity.ok(artisanUpdated);

			} else if (optionalArtisan.isPresent()
					&& emailValidator.checkValidAndExistEmail(artisanUpdated.getEmail())) {

				artisanRepository.save(artisanUpdated);
				return ResponseEntity.ok(artisanUpdated);

			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("el correo ya existe o no es válido");

			}
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no existe el elemento o no has pasado id");

	}
	
	//DTO
	public List<ArtisanDTO> findAllArtisansDTO() {
		List<Artisan> artisanList = artisanRepository.findAll(); //Buscamos todos los artesanos
		ModelMapper modelMapper = new ModelMapper(); 
		List<ArtisanDTO> artisanDTOList = new ArrayList<>();

		artisanList.forEach(artisanElement -> { //Mapeamos los resultados en el nuevo array creado
			ArtisanDTO artisanDto = modelMapper.map(artisanElement, ArtisanDTO.class);
			System.out.println(artisanDto.toString());
			artisanDTOList.add(artisanDto);
		});

		return artisanDTOList;
	}

	//DTO
	public ArtisanProfileDTO artisanProfileDTO(String username){
		 Optional<Artisan> artisan = artisanRepository.findByUsername(username);//Buscamos el artesano por username
		 Integer artisanId = artisan.get().getArtisan_id();// Guardamos id
		    if(artisan.isPresent()) {
		    	
		    	ArtisanProfileDTO artisanProfileDTO = new ArtisanProfileDTO(); //Creamos un nuevo objeto para guardar los valores deseados
		    	artisanProfileDTO.setArtisan_id(artisan.get().getArtisan_id());
		    	artisanProfileDTO.setName(artisan.get().getName());    
		    	artisanProfileDTO.setSurnames(artisan.get().getSurnames());
		    	artisanProfileDTO.setUsername(artisan.get().getUsername());    
		    	artisanProfileDTO.setDescription(artisan.get().getDescription());
		    	artisanProfileDTO.setImage(artisan.get().getImage());
		    	Stream<Product> productsStream = productRepository.findAll().stream().filter(product -> product.getArtisan_id() == artisanId);// Recorremos la lista de productos entera y buscamos las coincidencias de las dos ID artisan
		    	List<Product> productList = productsStream.collect(Collectors.toList());// Lo pasamos a lista
		    	artisanProfileDTO.setProducts(productList); //Lo guardamos
		    	artisanProfileDTO.setProductsCount(productList.size()); // Contamos la cantidad de coincidencias
		    	Stream<Followers> followersStream = followersRepository.findAll().stream().filter(followers -> followers.getFollower_id() == artisanId);
		    	List<Followers> followersList =followersStream.collect(Collectors.toList());
		    	artisanProfileDTO.setFollowersCount(followersList.size());
		    	return artisanProfileDTO;
		    }
		    return null;
	}
	
	@Override
	public List<Artisan> findArtisanByEmailAndPassword(String email, String password) {
		
		return artisanRepository.findByEmailAndPassword(email, password);
		
	}
	
	public ResponseEntity<String> uploadPhoto(Integer artisanId, MultipartFile file) {

		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("File is empty");
			}
			
			//String originalFilename = file.getOriginalFilename();
	        String newFilename = artisanId + "_Artisan.png" /*+ originalFilename*/;

			// Get a reference to the resource directory & Create a path for the image file
			Path filePath = Paths.get("src/main/resources/static/images/artisans").resolve(newFilename);
			
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
	
	public List<String> getArtisanPhotoUrls(Integer artisanId) {
	    List<String> photoUrls = new ArrayList<>();
	    
	    // Define the base URL for accessing images
	    //String baseUrl = "/images/product/";

	    // Get a reference to the resource directory
	    Resource resourceDir = new ClassPathResource("static/images/artisans");

	    try {
	        // Get a list of files in the directory
	        File[] files = resourceDir.getFile().listFiles();

	        if (files != null) {
	            for (File file : files) {
	                // Filter files based on productId and extension
	                String filename = file.getName();
	                if (filename.startsWith(artisanId + "_")) {
	                    photoUrls.add(file.getAbsolutePath());
	                }
	            }
	        }
	    } catch (IOException e) {
	        // Handle the exception
	    }

	    return photoUrls;
	}

}
