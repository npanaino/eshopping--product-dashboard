package com.hackerrank.eshopping.product.dashboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query("SELECT p FROM Product p where p.category = :category AND p.availability = :availability")
	public Optional<Iterable<Product>> findAllByCategoryAndAvailability(@Param("category") String category,
			@Param("availability") Boolean availability);

	@Query("SELECT p FROM Product p where p.category = :category")
	public Optional<Iterable<Product>> findAllByCategory(@Param("category") String category);

}
