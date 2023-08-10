package com.Artisan.entities.DTOs;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ArtisanDTONameSurname {
	@Column(name = "name")
	String name;

	@Column(name = "image")
	String image;
}
