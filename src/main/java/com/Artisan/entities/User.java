package com.Artisan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer user_id;
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email ;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "surnames")
	private String surnames;
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "description")
	private String description;
	@Column(name = "image")
	private String image;
	
}
