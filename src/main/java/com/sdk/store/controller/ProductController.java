package com.sdk.store.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sdk.store.dto.product.request.ProductCreateRequest;
import com.sdk.store.dto.product.request.ProductUpdateRequest;
import com.sdk.store.dto.product.response.ProductResponse;
import com.sdk.store.entities.Product;
import com.sdk.store.exception.CustomException;
import com.sdk.store.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/API/v1/products")
public class ProductController {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct();
    }
    
    @GetMapping("/{id}")
    public Product getOneProduct(@PathVariable Long id) {
        try {
            return productService.getOneProductById(id);
        } catch (Exception e) {
            // Log the exception and return a proper error response
            logger.error("Error fetching product by id: " + id, e);
            throw new CustomException("Product not found", HttpStatus.NOT_FOUND); // Custom Exception handling
        }
    }
    
    @GetMapping("/barcode/{barcode}")
    public ProductResponse getOneProductByBarcode(@PathVariable String barcode) {
        try {
            return productService.getOneProductByBarcode(barcode);
        } catch (Exception e) {
            // Log the exception and return a proper error response
            logger.error("Error fetching product by barcode: " + barcode, e);
            throw new CustomException("Product with barcode " + barcode + " not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Ensures status code 201 on successful creation
    public Product createOneProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        try {
            return productService.createOneProduct(productCreateRequest);
        } catch (Exception e) {
            logger.error("Error creating product", e);
            throw new CustomException("Product creation failed", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public Product updateOneProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        try {
            return productService.updateOneProductById(id, productUpdateRequest);
        } catch (Exception e) {
            logger.error("Error updating product with id: " + id, e);
            throw new CustomException("Product update failed", HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public void deleteOneProduct(@PathVariable Long id) {
        try {
            productService.deleteOneProductById(id);
        } catch (Exception e) {
            logger.error("Error deleting product with id: " + id, e);
            throw new CustomException("Product deletion failed", HttpStatus.BAD_REQUEST);
        }
    }    
}
