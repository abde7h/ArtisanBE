package com.Artisan.services.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.Artisan.entities.User;

public interface IUserService {
	
	List<User> findAllUsers();
	Optional<User> findUserById(Integer id);
	Optional<User> findUserByEmail(String email);
	ResponseEntity<Object> saveUser(User userAdd);
	String deleteUser(Integer id);
	ResponseEntity<Object> updateUser(User userUpdated);
	List<User> findUserByEmailAndPassword(String email, String password);
	
}
