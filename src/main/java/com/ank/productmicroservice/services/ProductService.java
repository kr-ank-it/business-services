package com.ank.productmicroservice.services;

import com.ank.productmicroservice.exceptions.ProductNotFoundException;
import com.ank.productmicroservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(Product product);
    boolean deleteProduct(Long id);
    Page<Product> getProducts(String title, int pageNumber, int pageSize);

}
