package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.Artisan.entities.User;

public interface IUserService {
	
	List<User> findAllUsers();
	Optional<User> findUserById(Long id);
	User saveUser(User user);
	String deleteUser(Long id);
	String updateUser(User userUpdated);

}
