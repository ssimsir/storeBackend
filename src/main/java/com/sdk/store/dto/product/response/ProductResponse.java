package com.sdk.store.dto.product.response;

import com.sdk.store.entities.Product;

import lombok.Data;

@Data
public class ProductResponse {
	private Long id;
	private Long userId;
	private String name;
	private String description;
	private String code;
	private String barcode;
	private int quantity;
	private int price;
	
	public ProductResponse(Product productEntity) {
		this.id = productEntity.getId();
		this.userId = productEntity.getUser().getUserId();		
		this.name = productEntity.getName();
		this.description = productEntity.getDescription();
		this.code = productEntity.getCode();
		this.barcode = productEntity.getBarcode();
		this.quantity = productEntity.getQuantity();
		this.price = productEntity.getPrice();
	}
}
