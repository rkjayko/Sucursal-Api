package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.ProductRequestDTO;
import com.example.franchiseapi.dto.ProductResponseDTO;
import com.example.franchiseapi.entity.Product;

public interface ProductServiceInterface {
    void deleteProduct(Long branchId, Long productId);

    ProductResponseDTO addProductToBranch(Long branchId, ProductRequestDTO productRequestDTO);

    Product updateProductStock(Long productId, Integer newStock);

    ProductResponseDTO updateProductName(Long productId, Long branchId, String newName);
}