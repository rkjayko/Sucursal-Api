package com.example.franchiseapi.controller;

import com.example.franchiseapi.dto.ProductResponseDTO;
import com.example.franchiseapi.dto.ProductStockUpdateDTO;
import com.example.franchiseapi.dto.ProductUpdateDTO;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.services.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateProductStock(@PathVariable Long id,
                                                      @RequestBody @Valid ProductStockUpdateDTO productStockUpdateDTO) {
        Product updatedProduct = productService.updateProductStock(id, productStockUpdateDTO.getStock());
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProductName(
            @PathVariable Long productId,
            @RequestParam Long branchId,
            @RequestBody @Valid ProductUpdateDTO productUpdateDTO) {

        ProductResponseDTO updatedProduct = productService.updateProductName(productId, branchId, productUpdateDTO.getName());
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
