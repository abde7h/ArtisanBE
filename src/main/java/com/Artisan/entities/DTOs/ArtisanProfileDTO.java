package com.Artisan.entities.DTOs;

import java.util.List;

import com.Artisan.entities.Followers;
import com.Artisan.entities.Product;

import lombok.Data;

@Data
public class ArtisanProfileDTO {
	
	String name;
	Integer Artisan_id;
	String surnames;
	String username;
	String description;
	String image;
	int followersCount;
	int productsCount;
	List<Product> products;
	List<Followers> followers;

}

