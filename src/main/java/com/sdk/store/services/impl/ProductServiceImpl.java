package com.sdk.store.services.impl;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sdk.store.dto.product.request.ProductCreateRequest;
import com.sdk.store.dto.product.request.ProductUpdateRequest;
import com.sdk.store.dto.product.response.ProductResponse;
import com.sdk.store.entities.Product;
import com.sdk.store.entities.User;
import com.sdk.store.repository.ProductRepository;
import com.sdk.store.services.ProductService;
import com.sdk.store.services.security.UserService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private UserService userService;
	
	public ProductServiceImpl(ProductRepository productRepository, UserService userService) {
		this.productRepository=productRepository;
		this.userService=userService;
	}
	
	
	
	public List<ProductResponse> getAllProduct() {
		List<Product> list = productRepository.findAll();
		return list.stream().map(p -> new ProductResponse(p)).collect(Collectors.toList());
	}
	
	
	public Product getOneProductById(Long likeId) {
		return productRepository.findById(likeId).orElse(null);
	}
	
	
	public ProductResponse getOneProductByBarcode(String barcode) {
		Product product = productRepository.findByBarcode (barcode).orElse(null);
		return new ProductResponse(product);
	}
	
	public Product createOneProduct(ProductCreateRequest newProductRequest){
		User user= userService.getOneUserById(newProductRequest.getUserId());
		if (user==null){
			return null;
		}
		Product toSaveProduct = new Product(
				newProductRequest.getId(), 
				user, 
				newProductRequest.getName(), 
				newProductRequest.getDescription(),
				newProductRequest.getCode(),
				newProductRequest.getBarcode(),
				newProductRequest.getQuantity(),
				newProductRequest.getPrice()
		);
		return productRepository.save(toSaveProduct);
	}
	
	public Product updateOneProductById(Long id,
			ProductUpdateRequest productUpdateRequest){
		
		Optional<Product> optionalProduct  = productRepository.findById(id);
		if (optionalProduct .isPresent()) {
			Product toUpdateProduct=optionalProduct.get();
	        toUpdateProduct.setName(productUpdateRequest.getName());
	        toUpdateProduct.setDescription(productUpdateRequest.getDescription());
	        toUpdateProduct.setCode(productUpdateRequest.getCode());
	        toUpdateProduct.setBarcode(productUpdateRequest.getBarcode());
	        toUpdateProduct.setQuantity(productUpdateRequest.getQuantity());
	        toUpdateProduct.setPrice(productUpdateRequest.getPrice());

			productRepository.save(toUpdateProduct);
			return toUpdateProduct;
		}
		return null;
	}
	
	public void deleteOneProductById(Long likeId) {
		productRepository.deleteById(likeId);
		
	}

}