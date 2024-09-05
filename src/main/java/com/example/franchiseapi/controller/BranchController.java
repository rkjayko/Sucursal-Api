package com.example.franchiseapi.controller;

import com.example.franchiseapi.dto.request.BranchUpdate;
import com.example.franchiseapi.dto.request.Product;
import com.example.franchiseapi.dto.response.BranchDTO;
import com.example.franchiseapi.dto.response.ProductDTO;
import com.example.franchiseapi.services.BranchServiceImpl;
import com.example.franchiseapi.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Branch", description = "Branch management APIs")
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchServiceImpl branchServiceImpl;
    private final ProductServiceImpl productService;

    @Autowired
    public BranchController(BranchServiceImpl branchServiceImpl, ProductServiceImpl productService) {
        this.branchServiceImpl = branchServiceImpl;
        this.productService = productService;
    }

    @PostMapping("/{branchId}/products")
    @Operation(summary = "Add a product to one branch", description = "")
    public ResponseEntity<ProductDTO> addProductToBranch(
            @PathVariable Long branchId,
            @Valid @RequestBody Product productRequest) {

        ProductDTO newProduct = productService.addProductToBranch(branchId, productRequest);
        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping("/{branchId}/products/{productId}")
    @Operation(summary = "Delete a product to one branch", description = "")
    public ResponseEntity<String> deleteProductFromBranch(
            @PathVariable Long branchId,
            @PathVariable Long productId) {
        productService.deleteProduct(branchId, productId);
        return ResponseEntity.ok().body("Deleted product");
    }

    @PutMapping("/{branchId}")
    @Operation(summary = "Update one branch with new name", description = "")
    public ResponseEntity<BranchDTO> updateBranchName(
            @PathVariable Long branchId,
            @RequestParam Long franchiseId,
            @RequestBody @Valid BranchUpdate branchUpdate) {

        BranchDTO updatedBranch = branchServiceImpl.updateBranchName(branchId, franchiseId, branchUpdate.getName());
        return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
    }
}