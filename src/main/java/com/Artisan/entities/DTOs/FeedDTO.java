package com.Artisan.entities.DTOs;

import lombok.Data;

@Data
public class FeedDTO {

	int id;
	String image;
	String name;
	ArtisanDTONameSurname artisan;
}
