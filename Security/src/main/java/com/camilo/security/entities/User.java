package com.camilo.security.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String username;
	private String password;
}
