package com.sdk.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdk.store.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByUserUserId(Long userId);
	
	Optional<Product> findByBarcode(String barcode);

}
