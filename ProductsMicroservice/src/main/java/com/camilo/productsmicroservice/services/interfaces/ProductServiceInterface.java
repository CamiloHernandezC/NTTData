package com.camilo.productsmicroservice.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.camilo.productsmicroservice.DTO.ProductDTO;
import com.camilo.productsmicroservice.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public interface ProductServiceInterface {

	Optional<ProductDTO> findById(Long id);
	
	List<ProductDTO> findall(Specification<Product> productSpec);

	void delete(Long id);

	ProductDTO save(ProductDTO newProduct);

	ProductDTO update(Long id, ProductDTO updatedProduct);
}
