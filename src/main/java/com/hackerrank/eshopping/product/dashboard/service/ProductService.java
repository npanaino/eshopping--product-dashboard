package com.hackerrank.eshopping.product.dashboard.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerrank.eshopping.product.dashboard.exception.ProductServiceException;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Product findById(Long id) {
		try {
			return productRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ProductServiceException("Product doesn't exists.");
		}
	}

	public void create(Product product) throws ProductServiceException {
		if (!productRepository.existsById(product.getId())) {
			productRepository.save(product);
		} else {
			throw new ProductServiceException("Product already exists.");
		}
	}

	public void update(Long id, Product productToUpdate) throws ProductServiceException {
		try {
			Product product = productRepository.findById(id).get();
			product.setName(productToUpdate.getName() != null ? productToUpdate.getName() : product.getName());
			product.setCategory(
					productToUpdate.getCategory() != null ? productToUpdate.getName() : product.getCategory());
			product.setRetailPrice(productToUpdate.getRetailPrice() != null ? productToUpdate.getRetailPrice()
					: product.getRetailPrice());
			product.setDiscountedPrice(productToUpdate.getDiscountedPrice() != null
					? productToUpdate.getDiscountedPrice() : product.getDiscountedPrice());
			product.setAvailability(productToUpdate.getAvailability() != null ? productToUpdate.getAvailability()
					: product.getAvailability());

			productRepository.save(product);
		} catch (NoSuchElementException e) {
			throw new ProductServiceException("Product doesn't exists.");
		}
	}

	public Iterable<Product> findAllByCategory(String category) {
		try {
			return productRepository.findAllByCategory(category).get();
		} catch (NoSuchElementException e) {
			throw new ProductServiceException("There is no product with that criteria.");
		}
	}

	public Iterable<Product> findAllByCategoryAndAvailability(String category, Boolean availability) {
		try {
			return productRepository.findAllByCategoryAndAvailability(category, availability).get();
		} catch (NoSuchElementException e) {
			throw new ProductServiceException("There is no product with that criteria.");
		}
	}

	public Iterable<Product> findAll() {
		try {
			return productRepository.findAll();
		} catch (NoSuchElementException e) {
			throw new ProductServiceException("There is no product with that criteria.");
		}
	}

}
