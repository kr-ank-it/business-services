package com.ank.productmicroservice.services;

import com.ank.productmicroservice.dtos.FakeStoreProductDto;
import com.ank.productmicroservice.exceptions.ProductNotFoundException;
import com.ank.productmicroservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.ank.productmicroservice.dtos.FakeStoreProductDto.getProductFromDto;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private RedisTemplate redisTemplate;
    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
//        throw new ProductNotFoundException(id, "This product does not exist");

        // Check if the product is cached in Redis
        Product cachedProduct = (Product) redisTemplate.opsForHash().get("PRODUCTS", "Products_" + id.toString());
        if(cachedProduct != null) {
            return cachedProduct;
        }
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);
        FakeStoreProductDto dto = response.getBody();
        cachedProduct = getProductFromDto(dto);
        if(cachedProduct == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found.");
        }
        // cache the product in Redis
        redisTemplate.opsForHash().put("PRODUCTS", "Products_" + id.toString(), cachedProduct);
        return cachedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products/",
                FakeStoreProductDto[].class);
        FakeStoreProductDto[] dto = response.getBody();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : dto){
            products.add(getProductFromDto(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public Page<Product> getProducts(String title, int pageNumber, int pageSize) {
        return null;
    }
}
