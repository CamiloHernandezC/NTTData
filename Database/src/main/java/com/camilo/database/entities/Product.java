package com.camilo.database.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "products")
public class Product {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private String imagePath;
	private double price;

}
