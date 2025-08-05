package com.ank.productmicroservice.repositories;

import com.ank.productmicroservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String name);

    Category save(Category category);
}
