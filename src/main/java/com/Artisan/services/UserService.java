package com.Artisan.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.stereotype.Service;

import com.Artisan.entities.User;
import com.Artisan.repository.UserRepository;
import com.Artisan.services.interfaces.IUserService;

@Service

public class UserService implements IUserService {

	UserRepository userRepository;

	public UserService(UserRepository userRepository) {

		this.userRepository = userRepository;

	}

	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
		
	}

	@Override
	public Optional<User> findUserById(Long id) {
		
		return userRepository.findById(id);
		
	}

	@Override
	public User saveUser(User user) throws DataIntegrityViolationException {
		
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			
			throw new DataIntegrityViolationException("El correo electrónico ya está en uso.");
			
		}
		
		userRepository.save(user);
		return user;
		
	}

	@Override
	public String deleteUser(Long id) {
		
		if (userRepository.findById(id).isPresent()) {
			
			userRepository.deleteById(id);
			return "User eliminado correctamente.";
			
		}
		
		return "Error! El customer no existe";
		
	}

	@Override
	public String updateUser(User userUpdated) {
		
		int num = userUpdated.getUser_id();
		
		if (userRepository.findById((long) num).isPresent()) {
			
			User userToUpdate = new User();
			userToUpdate.setUser_id(userUpdated.getUser_id());
			userToUpdate.setName(userUpdated.getName());
			userToUpdate.setEmail(userUpdated.getEmail());
			userToUpdate.setPassword(userUpdated.getPassword());
			userToUpdate.setName(userUpdated.getName());
			userToUpdate.setSurnames(userUpdated.getSurnames());
			userToUpdate.setTelephone(userUpdated.getTelephone());
			userToUpdate.setDescription(userUpdated.getDescription());
			userToUpdate.setImage(userUpdated.getImage());
			userRepository.save(userToUpdate);
			return "User modificado";
			
		}
		
		return "Error al modificar el User";
		
	}

}
