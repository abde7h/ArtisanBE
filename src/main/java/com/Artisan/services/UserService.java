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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Artisan.entities.Artisan;
import com.Artisan.entities.Likes;
import com.Artisan.entities.Product;
import com.Artisan.entities.User;
import com.Artisan.entities.DTOs.FollowersDTO;
import com.Artisan.entities.DTOs.UserProfileDTO;
import com.Artisan.helpers.EmailValidatorUser;
import com.Artisan.repository.ArtisanRepository;
import com.Artisan.repository.LikesRepository;
import com.Artisan.repository.ProductRepository;
import com.Artisan.repository.UserRepository;
import com.Artisan.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	UserRepository userRepository;
	LikesRepository likesRepository;
	ProductRepository productRepository;
	ArtisanRepository artisanRepository;

	EmailValidatorUser emailValidator = new EmailValidatorUser(this);

	public UserService(UserRepository userRepository, LikesRepository likesRepository,
			ProductRepository productRepository, ArtisanRepository artisanRepository) {
		this.userRepository = userRepository;
		this.likesRepository = likesRepository;
		this.productRepository = productRepository;
		this.artisanRepository = artisanRepository;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findUserById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public ResponseEntity<Object> saveUser(User userAdd) {
		if (emailValidator.checkValidAndExistEmail(userAdd.getEmail())) {
			userRepository.save(userAdd);
			userAdd.setImage("C:\\Users\\Tarda\\Desktop\\ArtisanBE\\target\\classes\\static\\images\\users\\" + userAdd.getUser_id() + "_User");
			userRepository.save(userAdd);
			return ResponseEntity.ok(userAdd);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya existe o no es válido");
		}
	}

	@Override
	public String deleteUser(Integer id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return "User eliminado correctamente.";
		}
		return "Error! El User no existe";
	}

	@Override
	public ResponseEntity<Object> updateUser(User userUpdated) {
		Optional<User> optionalUser = null;
		if (userUpdated.getUser_id() != null) {

			optionalUser = userRepository.findById(userUpdated.getUser_id());

			if (userUpdated.getEmail().equals(optionalUser.get().getEmail())) {

				userRepository.save(userUpdated);
				return ResponseEntity.ok(userUpdated);

			} else if (optionalUser.isPresent() && emailValidator.checkValidAndExistEmail(userUpdated.getEmail())) {
				userRepository.save(userUpdated);
				return ResponseEntity.ok(userUpdated);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya existe o no es válido");
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el elemento o no has pasado el id");
	}

	@Override
	public List<User> findUserByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public List<FollowersDTO> findArtisansFollowedByUser(String username) {
		return userRepository.findArtisansFollowedByUser(username);
	}
	
	public UserProfileDTO asdf(String username) {
	   // Optional<User> userSelected = userRepository.findById(id);
	    Optional<User> userSelected = userRepository.findByUsername(username);
	    List<Likes> userLikes = likesRepository.findAll().stream()
	            .filter(likesData -> likesData.getUser_id().equals(userSelected.get().getUser_id()))
	            .collect(Collectors.toList());

	    List<Product> products = productRepository.findAll().stream()
	            .filter(productData -> userLikes.stream().anyMatch(likesData -> likesData.getProduct_id().equals(productData.getProduct_id())))
	            .collect(Collectors.toList());

	    List<Artisan> artisans = artisanRepository.findAll().stream()
	            .filter(artisanData -> products.stream().anyMatch(productData -> productData.getArtisan_id().equals(artisanData.getArtisan_id())))
	            .collect(Collectors.toList());

	    UserProfileDTO userProfileDTO = new UserProfileDTO();

	    if (userSelected.isPresent()) {
	    	userProfileDTO.setUsername(userSelected.get().getUsername());
	        userProfileDTO.setName(userSelected.get().getName());
	        userProfileDTO.setSurnames(userSelected.get().getSurnames());
	        userProfileDTO.setImage(userSelected.get().getImage());
	        userProfileDTO.setDescription(userSelected.get().getDescription());
	        userProfileDTO.setListproduct(products);
	        userProfileDTO.setListartisan(artisans);
	    }

	    return userProfileDTO;
	}
	
	public ResponseEntity<String> uploadPhoto(Integer userId, MultipartFile file) {

		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("File is empty");
			}
			
			//String originalFilename = file.getOriginalFilename();
	        String newFilename = userId + "_User.png" /*+ originalFilename*/;

			// Get a reference to the resource directory & Create a path for the image file
			Path filePath = Paths.get("src/main/resources/static/images/users").resolve(newFilename);
			
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
	
	public List<String> getArtisanPhotoUrls(Integer userId) {
	    List<String> photoUrls = new ArrayList<>();
	    
	    // Define the base URL for accessing images
	    //String baseUrl = "/images/product/";

	    // Get a reference to the resource directory
	    Resource resourceDir = new ClassPathResource("static/images/users");

	    try {
	        // Get a list of files in the directory
	        File[] files = resourceDir.getFile().listFiles();

	        if (files != null) {
	            for (File file : files) {
	                // Filter files based on productId and extension
	                String filename = file.getName();
	                if (filename.startsWith(userId + "_")) {
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
