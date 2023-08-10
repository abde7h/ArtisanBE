package com.Artisan.entities.DTOs;

import java.util.List;

import com.Artisan.entities.Likes;

import lombok.Data;

@Data
public class ProductProfileDTO {
	String name;
	String image;
	String description;
	String price;
	String creation_date;
	int likesCount;
	List<Likes> likes;
	

}
