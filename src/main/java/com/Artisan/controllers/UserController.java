package com.Artisan.controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Artisan.entities.User;
import com.Artisan.services.UserService;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/1.0.0")


public class UserController {
	@Autowired(required = false)
	UserService userService;
	@RequestMapping("/user")
	public List<User> getUser(){
		log.info("Request a http://localhost:PORT/1.0.0/user (GET)");
		return userService.findAllUsers();
		
	}
	@RequestMapping("/user/{idUsuario}")
	public Optional<User> findUserById(@PathVariable Long idUsuario){
		log.info("Request a http://localhost:PORT/1.0.0/user/"+idUsuario+" (GET)");
		Optional<User> user = userService.findUserById(idUsuario);
	 return user;
		
	}
	@PostMapping("/user/add")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
	 log.info("Request a http://localhost:PORT/1.0.0/user/add (POST)");
	 User savedUser = userService.saveUser(user);
	 if (savedUser != null) {
	 return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	 } else {
	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 }
	}
	@DeleteMapping("/user/delete/{idUsuario}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long idUsuario){
		 log.info("Request a http://localhost:PORT/1.0.0/user/delete/" + idUsuario + " (DELETE)");
		 String result = userService.deleteUser(idUsuario);
		 if (result.equals("User eliminado correctamente.")) {
		 return ResponseEntity.noContent().build(); 
		 } else {
		 return ResponseEntity.notFound().build();
		 }
		
	
		
	}
	@PatchMapping("/user/update")
	public ResponseEntity<String> updateUser(@RequestBody User userUpdated) {
	 log.info("Request a http://localhost:PORT/1.0.0/user/update (PATCH)");
	 Long userId = (long) userUpdated.getId();
	 Optional<User> existingCustomer = userService.findUserById(userId);
	 if (existingCustomer.isPresent()) {
	 userService.updateUser(userUpdated);
	 return ResponseEntity.ok("User modificado");
	 } else {
	 return ResponseEntity.notFound().build();
	 }
	}


}
