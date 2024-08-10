package com.example.franchiseapi.controller;

import com.example.franchiseapi.dto.BranchResponseDTO;
import com.example.franchiseapi.dto.BranchUpdateDTO;
import com.example.franchiseapi.dto.ProductRequestDTO;
import com.example.franchiseapi.dto.ProductResponseDTO;
import com.example.franchiseapi.services.BranchService;
import com.example.franchiseapi.services.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    private final IProductService productService;

    @Autowired
    public BranchController(BranchService branchService, IProductService productService) {
        this.branchService = branchService;
        this.productService = productService;
    }

    @PostMapping("/{branchId}/products")
    public ResponseEntity<ProductResponseDTO> addProductToBranch(
            @PathVariable Long branchId,
            @Valid @RequestBody ProductRequestDTO productRequest) {

        ProductResponseDTO newProduct = productService.addProductToBranch(branchId, productRequest);
        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping("/{branchId}/products/{productId}")
    public ResponseEntity<String> deleteProductFromBranch(
            @PathVariable Long branchId,
            @PathVariable Long productId) {
        productService.deleteProduct(branchId, productId);
        return ResponseEntity.ok().body("Deleted product");
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<BranchResponseDTO> updateBranchName(
            @PathVariable Long branchId,
            @RequestParam Long franchiseId,
            @RequestBody @Valid BranchUpdateDTO branchUpdateDTO) {

        BranchResponseDTO updatedBranch = branchService.updateBranchName(branchId, franchiseId, branchUpdateDTO.getName());
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }
}