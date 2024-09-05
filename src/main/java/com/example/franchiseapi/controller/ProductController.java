package com.example.franchiseapi.controller;

import com.example.franchiseapi.dto.request.ProductStockUpdate;
import com.example.franchiseapi.dto.request.ProductUpdate;
import com.example.franchiseapi.dto.response.ProductDTO;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update a stock with a new name", description = "")
    public ResponseEntity<Product> updateProductStock(@PathVariable Long id,
                                                      @RequestBody @Valid ProductStockUpdate productStockUpdate) {
        Product updatedProduct = productService.updateProductStock(id, productStockUpdate.getStock());
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProductName(
            @PathVariable Long productId,
            @RequestParam Long branchId,
            @RequestBody @Valid ProductUpdate productUpdate) {

        ProductDTO updatedProduct = productService.updateProductName(productId, branchId, productUpdate.getName());
        return ResponseEntity.ok(updatedProduct);
    }
}
