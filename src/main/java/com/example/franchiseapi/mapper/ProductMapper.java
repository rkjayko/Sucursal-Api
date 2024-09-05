package com.example.franchiseapi.mapper;

import com.example.franchiseapi.dto.response.ProductDTO;
import com.example.franchiseapi.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(com.example.franchiseapi.dto.request.Product productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setStock(productDTO.getStock());
        return product;
    }

    public ProductDTO toResponseDTO(Product product) {
        return new ProductDTO(product.getName(), product.getStock());
    }
}
