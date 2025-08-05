package com.ank.productmicroservice.services;

import com.ank.productmicroservice.exceptions.CategoryNotFoundException;
import com.ank.productmicroservice.exceptions.ProductNotFoundException;
import com.ank.productmicroservice.models.Category;
import com.ank.productmicroservice.models.Product;
import com.ank.productmicroservice.repositories.CategoryRepository;
import com.ank.productmicroservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class SelfProductService implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
//        Optional<Product> product = productRepository.findById(id);
//        if(product.isEmpty()){
//            throw new ProductNotFoundException(id, "Product not found");
//        }
//        return product.get();

        return productRepository
                .findById(id)
                .orElseThrow(
                        () -> new ProductNotFoundException(id, "Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
        // return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        if(category == null) {
            throw new CategoryNotFoundException("Category is mandatory for creation of the product");
        }

        Optional<Category> categoryOptional = categoryRepository.findByTitle(category.getTitle());
        if(categoryOptional.isEmpty()) {
            // Category does not exist. So create a new category
            category = categoryRepository.save(category);
        }else{
            category = categoryOptional.get();
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public Page<Product> getProducts(String title, int pageNumber, int pageSize) {

        return productRepository.findByTitleContainingIgnoreCase(title, PageRequest.of(pageNumber, pageSize));

    }
}
