package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Artisan.entities.User;
import com.Artisan.helpers.EmailValidatorUser;
import com.Artisan.repository.UserRepository;
import com.Artisan.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	UserRepository userRepository;
	EmailValidatorUser emailValidator = new EmailValidatorUser(this);

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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
			
		}else {
			
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
			
			if(userUpdated.getEmail().equals(optionalUser.get().getEmail())) {
				
				userRepository.save(userUpdated);
				return ResponseEntity.ok(userUpdated);
				
			} else if (optionalUser.isPresent() && emailValidator.checkValidAndExistEmail(userUpdated.getEmail())) {
				
				userRepository.save(userUpdated);
				return ResponseEntity.ok(userUpdated);
				
			}
			else {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo ya existe o no es válido");
				
			}
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el elemento o no has pasado el id");
		
	}
}
