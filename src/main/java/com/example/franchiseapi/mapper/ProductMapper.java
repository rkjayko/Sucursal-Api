package com.example.franchiseapi.mapper;

import com.example.franchiseapi.dto.ProductRequestDTO;
import com.example.franchiseapi.dto.ProductResponseDTO;
import com.example.franchiseapi.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setStock(productRequestDTO.getStock());
        return product;
    }

    public ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(product.getName(), product.getStock());
    }
}
