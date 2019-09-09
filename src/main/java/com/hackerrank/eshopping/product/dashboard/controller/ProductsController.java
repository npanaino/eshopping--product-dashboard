package com.hackerrank.eshopping.product.dashboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.exception.BadRequestException;
import com.hackerrank.eshopping.product.dashboard.exception.NotFoundException;
import com.hackerrank.eshopping.product.dashboard.exception.ProductServiceException;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductsController {

	@Autowired
	ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	// 1. Add a product
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addProduct(@RequestBody(required = true) ProductDTO productDTO) {
		try {
			productService.create(modelMapper.map(productDTO, Product.class));
		} catch (ProductServiceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	// 2. Update a product by id
	@PutMapping("/{product_id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateProduct(@PathVariable("product_id") Long id,
			@RequestBody(required = true) @Valid ProductDTO productDTO) {
		try {
			productService.update(id, modelMapper.map(productDTO, Product.class));
		} catch (ProductServiceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	// 3. Return a product by id
	@GetMapping("/{product_id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO getProductById(@PathVariable("product_id") Long id) {
		ProductDTO productDTO = new ProductDTO();
		try {
			productDTO = modelMapper.map(productService.findById(id), ProductDTO.class);
		} catch (ProductServiceException e) {
			throw new NotFoundException(e.getMessage());
		}
		return productDTO;
	}

	// 4. Return products by category
	// 5. Return products by category and availability
	// 6. Return all products
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProductDTO> getProducts(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "availability", required = false) Boolean availability) {
		List<ProductDTO> products = new ArrayList<ProductDTO>();

		try {
			if (category != null) {
				category = URLDecoder.decode(category, "UTF-8");
				if (availability != null) {
					(productService.findAllByCategoryAndAvailability(category, availability)).forEach((product) -> {
						products.add(modelMapper.map(product, ProductDTO.class));
						products.sort(Comparator.comparingInt(ProductDTO::getDiscountPercentage).reversed()
								.thenComparingDouble(ProductDTO::getDiscountedPrice)
								.thenComparingLong(ProductDTO::getId));
					});
				} else {
					(productService.findAllByCategory(category)).forEach((product) -> {
						products.add(modelMapper.map(product, ProductDTO.class));
						products.sort(Comparator.comparing(ProductDTO::getAvailability).reversed()
								.thenComparingDouble(ProductDTO::getDiscountedPrice)
								.thenComparingLong(ProductDTO::getId));
					});
				}

			} else {
				(productService.findAll()).forEach((product) -> {
					products.add(modelMapper.map(product, ProductDTO.class));
					products.sort(Comparator.comparingLong(ProductDTO::getId));
				});
			}
		} catch (UnsupportedEncodingException e1) {
			System.out.println("There was an error decoding URL parameter");
		} catch (ProductServiceException e) {
			System.out.println(e.getMessage());
		}

		System.out.println();
		System.out.println();
		products.forEach(p -> System.out.println("id:" + p.getId() + "," + "dpercentaje:" + p.getDiscountPercentage() + "," + "dprice:" + p.getDiscountedPrice()));
		System.out.println();
		System.out.println();

		return products;
	}

}
