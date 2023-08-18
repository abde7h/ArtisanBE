package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Artisan.entities.Artisan;
import com.Artisan.entities.Likes;
import com.Artisan.entities.Product;
import com.Artisan.entities.User;
import com.Artisan.entities.DTOs.ArtisanDTONameSurname;
import com.Artisan.entities.DTOs.ProductDTO;
import com.Artisan.entities.DTOs.UserProfileDTO;
import com.Artisan.entities.DTOs.UserProfileLikesDTO;
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

	/*
	 * public UserProfileDTO createProfileDTO() { Optional<User> userSelected =
	 * userRepository.findById(1); Optional<Likes> userLikes =
	 * likesRepository.findAll() .stream() .filter(likesData ->
	 * likesData.getUser_id().equals(userSelected.get().getUser_id())) .findFirst();
	 * 
	 * UserProfileDTO userProfileDTO = new UserProfileDTO();
	 * 
	 * if (userLikes.isPresent() && userSelected.isPresent()) {
	 * userProfileDTO.setName(userSelected.get().getName());
	 * userProfileDTO.setSurnames(userSelected.get().getSurnames());
	 * userProfileDTO.setImage(userSelected.get().getImage());
	 * userProfileDTO.setDescription(userSelected.get().getDescription());
	 * 
	 * UserProfileLikesDTO userProfileLikesDTO = new UserProfileLikesDTO();
	 * 
	 * // Map ProductDTO ProductDTO productDTO = new ProductDTO();
	 * productDTO.setName(userLikes.get().getProduct().getName());
	 * productDTO.setImage(userLikes.get().getProduct().getImage());
	 * productDTO.setProduct_id(userLikes.get().getProduct().getProduct_id());
	 * userProfileLikesDTO.setProduct(productDTO);
	 * 
	 * // Map ArtisanDTONameSurname ArtisanDTONameSurname artisanDTO = new
	 * ArtisanDTONameSurname();
	 * artisanDTO.setName(userLikes.get().getArtisan().getName());
	 * artisanDTO.setSurname(userLikes.get().getArtisan().getSurname());
	 * userProfileLikesDTO.setArtisan(artisanDTO);
	 * 
	 * userProfileDTO.setLikes(userProfileLikesDTO); }
	 * 
	 * return userProfileDTO; }
	 */

	public UserProfileDTO asdf() {
		Optional<User> userSelected = userRepository.findById(1);
		Optional<Likes> user = likesRepository.findAll().stream()
				.filter(likesData -> likesData.getUser_id().equals(userSelected.get().getUser_id())).findFirst();
		List<Product> product = productRepository.findAll().stream()
				.filter(productData -> productData.getProduct_id().equals(user.get().getProduct_id())).toList();
//		List<Artisan> artisan = artisanRepository.findAll().stream()
//				.filter(artisanData -> artisanData.getArtisan_id().equals(product.get().getArtisan_id())).toList();

		UserProfileDTO userProfileDTO = new UserProfileDTO();
		if (user.isPresent() && userSelected.isPresent()) {
			userProfileDTO.setName(userSelected.get().getName());
			userProfileDTO.setSurnames(userSelected.get().getSurnames());
			userProfileDTO.setImage(userSelected.get().getImage());
			userProfileDTO.setDescription(userSelected.get().getDescription());
			userProfileDTO.setList(product);

		}

		return userProfileDTO;
	}

}
