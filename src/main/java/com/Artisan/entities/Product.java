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
@Table(name = "Product")

public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	int product_id;
	@Column(name = "artisan_id")
	int artisan_id;
	@Column(name = "name")
	String name;
	@Column(name = "image")
	String image;
	@Column(name = "description")
	String description;
	@Column(name = "price")
	String price;
	@Column(name = "category_id")
	int category_id;
	@Column(name = "creation_date")
	String creation_date;
	@Column(name = "sold")
	boolean sold;
	@Column(name = "user_id")
	int user_id;
	@Column(name = "buy_date")
	String buy_date;
	@Column(name = "visible")
	Boolean visible;

}
