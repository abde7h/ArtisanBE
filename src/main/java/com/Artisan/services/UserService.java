package com.Artisan.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.Artisan.entities.User;
import com.Artisan.repository.UserRepository;
@Service


public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
	public User saveUser(User user) throws DataIntegrityViolationException {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new DataIntegrityViolationException("El correo electrónico ya está en uso.");}
			userRepository.save(user);
		return user;
	}
	public String deleteUser(Long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return "User eliminado correctamente.";
		}
		return "Error! El customer no existe";
	}
	public String updateUser(User userUpdated) {
		int num = userUpdated.getId();
		if (userRepository.findById((long) num).isPresent()) {
			User userToUpdate = new User();
			userToUpdate.setId(userUpdated.getId());
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
