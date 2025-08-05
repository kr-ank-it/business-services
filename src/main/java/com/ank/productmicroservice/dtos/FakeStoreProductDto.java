package com.ank.productmicroservice.dtos;

import com.ank.productmicroservice.models.Category;
import com.ank.productmicroservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;

    public static Product getProductFromDto(FakeStoreProductDto dto) {
        if(dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        Category category = new Category();
        category.setTitle(dto.getCategory());
        product.setCategory(category);

        return product;
    }
}
