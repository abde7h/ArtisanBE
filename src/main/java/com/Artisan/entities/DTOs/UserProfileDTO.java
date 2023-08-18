package com.Artisan.entities.DTOs;

import java.util.List;

import com.Artisan.entities.Product;

import lombok.Data;

@Data
public class UserProfileDTO {
	// User
	private String name;
	private String surnames;
	private String image;
	private String description;
	// Product
	private List<Product> list;
//	String productName;
//	String productImage;
//	Integer productId;
//	String artisanUsername;
//	String artisanImage;
}
