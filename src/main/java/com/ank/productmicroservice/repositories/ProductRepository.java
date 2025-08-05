package com.ank.productmicroservice.repositories;

import com.ank.productmicroservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long productId);

    @Override
    List<Product> findAll();

    Product save(Product product);

    @Query("select p from products p where p.id = 1")
    Product fetchThings(Long productId);

    Page<Product> findByTitleContainingIgnoreCase(String title, PageRequest pageRequest);
}
