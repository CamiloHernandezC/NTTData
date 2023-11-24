package com.camilo.productsmicroservice.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.camilo.productsmicroservice.DTO.ProductDTO;
import com.camilo.productsmicroservice.entities.Product;
import com.camilo.productsmicroservice.repositories.ProductRepository;

@SpringBootTest
class ProductControllerTests {
	
	protected MockMvc mockMvc;

	@MockBean
	private ProductRepository repository;

	@Autowired
	WebApplicationContext wac;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	private static final String TEST_PRODUCT_NAME = "Product Name";
	private static final String TEST_PRODUCT_DESCRIPTION = "Product Description";
	private static final String TEST_IMAGE_PATH = "assets/product.png";
	private static final double TEST_PRICE = 5000;
	private final Product productMock = new Product(999L, TEST_PRODUCT_NAME, TEST_PRODUCT_DESCRIPTION, TEST_IMAGE_PATH, TEST_PRICE);
	private final ProductDTO productDTOMock = new ProductDTO(999L, TEST_PRODUCT_NAME, TEST_PRODUCT_DESCRIPTION, TEST_IMAGE_PATH, TEST_PRICE);

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	void findAll() throws Exception {
		when(repository.findAll(Mockito.any(Specification.class))).thenReturn(Collections.singletonList(productMock));
		mockMvc.perform(get("/products?name=name"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$[0].name").value(productMock.getName()))
		.andExpect(jsonPath("$[1]").doesNotExist());
	}
	
	@Test
	void deleteProduct() throws Exception {
		when(repository.findById(productMock.getId())).thenReturn(Optional.of(productMock));
		mockMvc.perform(MockMvcRequestBuilders.delete("/products/999"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());
	}
	
	@Test
	void deleteProduct_ProductNotFoundError() throws Exception {
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.delete("/products/999"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	void createProduct() throws Exception {
		when(repository.save(productMock)).thenReturn(productMock);
		ObjectMapper mapper = new ObjectMapper();
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(productDTOMock);
		mockMvc.perform(post("/products").contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isOk());
	}
}
