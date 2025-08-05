package com.ank.productmicroservice.controllerAdvice;

import com.ank.productmicroservice.dtos.ProductNotFoundExceptionDto;
import com.ank.productmicroservice.exceptions.ProductNotFoundException;
import com.ank.productmicroservice.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductServiceExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> handleProductNotFoundException(ProductNotFoundException e){
        ProductNotFoundExceptionDto productNotFoundExceptionDto = new ProductNotFoundExceptionDto();
        productNotFoundExceptionDto.setProductId(e.getProductId());
        productNotFoundExceptionDto.setMessage(e.getProductId() + " " + e.getMessage());
        ResponseEntity<ProductNotFoundExceptionDto> response = new ResponseEntity<>(
                productNotFoundExceptionDto,
                HttpStatus.NOT_FOUND);
        return response;
    }


}
