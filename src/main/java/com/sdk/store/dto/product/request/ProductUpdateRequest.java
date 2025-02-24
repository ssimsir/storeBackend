package com.sdk.store.dto.product.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
	private String name;
	private String description;
	private String code;
	private String barcode;
	private int quantity;
	private int price;
}
