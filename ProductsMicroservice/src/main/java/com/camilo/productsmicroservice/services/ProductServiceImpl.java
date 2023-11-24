package com.camilo.productsmicroservice.services;

import java.util.List;
import java.util.Optional;

import com.camilo.productsmicroservice.DTO.ProductDTO;
import com.camilo.productsmicroservice.converters.ProductConverter;
import com.camilo.productsmicroservice.entities.Product;
import com.camilo.productsmicroservice.exceptions.ProductNotFoundException;
import com.camilo.productsmicroservice.repositories.ProductRepository;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.camilo.productsmicroservice.services.interfaces.ProductServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServiceInterface {

	private final ProductRepository repository;
	private final ProductConverter converter;

	@Cacheable("myCache")
	@Override
	public List<ProductDTO> findall(Specification<Product> productSpec) {
		return converter.toDTO(repository.findAll(productSpec));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public ProductDTO save(ProductDTO newProduct) {
		Product entity = converter.toEntity(newProduct);
		entity = repository.save(entity);
		return converter.toDTO(entity);	}

	@CacheEvict("myCache")
	@Override
	public ProductDTO update(Long id, ProductDTO updatedProduct) {
		Optional<Product> optional = repository.findById(id);

		if (optional.isEmpty()) {
			throw new ProductNotFoundException();
		}

		Product existingProduct = optional.get();

		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setPrice(updatedProduct.getPrice());

		existingProduct = repository.save(existingProduct);

		return converter.toDTO(existingProduct);
	}

	@Cacheable("myCache")
	@Override
	public Optional<ProductDTO> findById(Long id) {
		Optional<Product> product = repository.findById(id);
		if(product.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(converter.toDTO(product.get()));
	}
}
