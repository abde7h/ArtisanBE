package com.Artisan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "User")

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	int user_id;
	@Column(name = "username")
	String username;
	@Column(name = "email")
	String email ;
	@Column(name = "password")
	String password;
	@Column(name = "name")
	String name;
	@Column(name = "surnames")
	String surnames;
	@Column(name = "telephone")
	String telephone;
	@Column(name = "description")
	String description;
	@Column(name = "image")
	String image;

}
