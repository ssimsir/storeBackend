package com.sdk.store.services;

import java.util.List;

import com.sdk.store.dto.product.request.ProductCreateRequest;
import com.sdk.store.dto.product.request.ProductUpdateRequest;
import com.sdk.store.dto.product.response.ProductResponse;
import com.sdk.store.entities.Product;


public interface ProductService{
	
	public List<ProductResponse> getAllProduct();
	
	public Product getOneProductById(Long id) ;
	
	public ProductResponse getOneProductByBarcode(String barcode) ;
	
	public Product createOneProduct(ProductCreateRequest newProductRequest);
	
	public Product updateOneProductById(Long id, ProductUpdateRequest productUpdateRequest);
	
	public void deleteOneProductById(Long id);

}