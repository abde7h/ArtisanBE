package com.Artisan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Likes")

public class Likes {
	
	@Id
	@Column(name = "user_id")
	int user_id;
	@Column(name = "product_id")
	int product_id;

}
