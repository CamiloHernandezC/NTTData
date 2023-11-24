package com.camilo.productsmicroservice.controllers;

import java.util.List;
import java.util.Optional;

import com.camilo.productsmicroservice.DTO.ProductDTO;
import com.camilo.productsmicroservice.entities.Product;
import com.camilo.productsmicroservice.exceptions.ProductNotFoundException;
import com.camilo.productsmicroservice.services.interfaces.ProductServiceInterface;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	
	private final ProductServiceInterface service;

	@GetMapping("")
	List<ProductDTO> findall(
			@And({
			@Spec(path = "description",	params = "description",	spec = Like.class),
			@Spec(path = "name",	params = "name",	spec = Like.class),
			@Spec(path = "price", params={"priceGreaterThan","priceLessThan"}, spec=Between.class)
			})
			Specification<Product> productSpec){
		
		return (service.findall(productSpec));
	}
	
	
	@DeleteMapping("/{id}")
	void deleteProduct(@PathVariable("id")final Long id){
		Optional<ProductDTO> optional = service.findById(id);
		if(optional.isEmpty()) {
			throw new ProductNotFoundException();
		}
		service.delete(id);
	}
	
	@PostMapping("")
	ProductDTO newProduct(@RequestBody ProductDTO newProduct) {
		return service.save(newProduct);
	}

	@PutMapping("/{id}")
	ProductDTO updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO updatedProduct) {
		return service.update(id, updatedProduct);
	}
}
