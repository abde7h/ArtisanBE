package com.Artisan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	public List<ArtisanDTO> findAllArtisansDTO() {
		List<Artisan> artisanList = artisanRepository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		List<ArtisanDTO> artisanDTOList = new ArrayList<>();

		artisanList.forEach(artisanElement -> {
			ArtisanDTO artisanDto = modelMapper.map(artisanElement, ArtisanDTO.class);
			System.out.println(artisanDto.toString());
			artisanDTOList.add(artisanDto);
		});

		return artisanDTOList;
	}

	public ArtisanProfileDTO artisanProfileDTO(String username){
		 Optional<Artisan> artisan = artisanRepository.findByUsername(username);
		 Integer artisanId = artisan.get().getArtisan_id();
		    if(artisan.isPresent()) {
		    	
		    	ArtisanProfileDTO artisanProfileDTO = new ArtisanProfileDTO();
		    	artisanProfileDTO.setUsername(artisan.get().getUsername());    
		    	artisanProfileDTO.setDescription(artisan.get().getDescription());
		    	artisanProfileDTO.setImage(artisan.get().getImage());
		    	Stream<Product> productsStream = productRepository.findAll().stream().filter(product -> product.getArtisan_id() == artisanId);
		    	List<Product> productList = productsStream.collect(Collectors.toList());
		    	artisanProfileDTO.setProducts(productList);
		    	artisanProfileDTO.setProductsCount(productList.size());
		    	Stream<Followers> followersStream = followersRepository.findAll().stream().filter(followers -> followers.getFollower_id() == artisanId);
		    	List<Followers> followersList =followersStream.collect(Collectors.toList());
		    	artisanProfileDTO.setFollowersCount(followersList.size());
		    	return artisanProfileDTO;
		    }
		    return null;
	}

}
