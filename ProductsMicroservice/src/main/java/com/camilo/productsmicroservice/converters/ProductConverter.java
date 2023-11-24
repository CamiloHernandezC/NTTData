package com.camilo.productsmicroservice.converters;

import java.util.ArrayList;
import java.util.List;

import com.camilo.productsmicroservice.DTO.ProductDTO;
import com.camilo.productsmicroservice.entities.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter {

	public ProductDTO toDTO(final Product Product) {
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(Product, ProductDTO.class);
	}
	
	public List<ProductDTO> toDTO(final List<Product> products) {
		List<ProductDTO> resultDTO = new ArrayList<>();
		final ModelMapper modelMapper = new ModelMapper();
		for (Product Product : products) {
			resultDTO.add(modelMapper.map(Product, ProductDTO.class));
		}
		return resultDTO;
	}
	
	public Product toEntity(final ProductDTO dto) {
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(dto, Product.class);
	}
}
