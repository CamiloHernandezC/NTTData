package com.camilo.productsmicroservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {
	
	private Long id;
	private String name;
	private String description;
	private String imagePath;
	private double price;

}
