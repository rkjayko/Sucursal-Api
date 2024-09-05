package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.response.ProductDTO;
import com.example.franchiseapi.entity.Product;

public interface ProductServiceInterface {
    void deleteProduct(Long branchId, Long productId);

    ProductDTO addProductToBranch(Long branchId, com.example.franchiseapi.dto.request.Product product);

    Product updateProductStock(Long productId, Integer newStock);

    ProductDTO updateProductName(Long productId, Long branchId, String newName);
}