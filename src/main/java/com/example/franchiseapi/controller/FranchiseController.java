package com.example.franchiseapi.controller;

import com.example.franchiseapi.dto.request.Franchise;
import com.example.franchiseapi.dto.request.FranchiseUpdate;
import com.example.franchiseapi.dto.response.FranchiseResponse;
import com.example.franchiseapi.dto.response.TopProductDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.services.FranchiseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Franchise", description = "Franchise management APIs")
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseServiceImpl franchiseService;

    @Autowired
    public FranchiseController(FranchiseServiceImpl franchiseService) {
        this.franchiseService = franchiseService;
    }

    @PostMapping
    @Operation(summary = "Add a new Franchise", description = "")
    public ResponseEntity<FranchiseResponse> addFranchise(@RequestBody @Valid Franchise franchise) {
        FranchiseResponse newFranchise = franchiseService.addFranchise(franchise);
        return new ResponseEntity<>(newFranchise, HttpStatus.CREATED);
    }

    @PostMapping("/{franchiseId}/branches")
    @Operation(summary = "Add one branch to Franchise", description = "")
    public ResponseEntity<Branch> addBranchToFranchise(
            @PathVariable Long franchiseId,
            @RequestBody @Valid Branch branch) {

        Branch newBranch = franchiseService.addBranchToFranchise(franchiseId, branch);
        return ResponseEntity.ok(newBranch);
    }

    @GetMapping("/{franchiseId}/top-products")
    @Operation(summary = "Get products from a top list branch", description = "")
    public ResponseEntity<List<TopProductDTO>> getTopProductsByStockInEachBranch(@PathVariable Long franchiseId) {
        List<Product> topProducts = franchiseService.getTopProductsByStockInEachBranch(franchiseId);
        List<TopProductDTO> result = topProducts.stream()
                .map(product -> new TopProductDTO(product.getName(), product.getStock(), product.getBranch().getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a franchise with a new name", description = "")
    public ResponseEntity<FranchiseResponse> updateFranchiseName(
            @PathVariable Long id,
            @RequestBody @Valid FranchiseUpdate franchiseUpdate) {

        FranchiseResponse updatedFranchise = franchiseService.updateFranchiseName(id, franchiseUpdate.getName());
        return new ResponseEntity<>(updatedFranchise, HttpStatus.OK);
    }
}