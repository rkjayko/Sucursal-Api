package com.example.franchiseapi.controller;

import com.example.franchiseapi.dto.FranchiseRequestDTO;
import com.example.franchiseapi.dto.FranchiseResponseDTO;
import com.example.franchiseapi.dto.FranchiseUpdateDTO;
import com.example.franchiseapi.dto.TopProductDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.services.IFranchiseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final IFranchiseService franchiseService;

    @Autowired
    public FranchiseController(IFranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }
    
    @PostMapping
    public ResponseEntity<FranchiseResponseDTO> addFranchise(@RequestBody @Valid FranchiseRequestDTO franchise) {
        FranchiseResponseDTO newFranchise = franchiseService.addFranchise(franchise);
        return new ResponseEntity<>(newFranchise, HttpStatus.CREATED);
    }

    @PostMapping("/{franchiseId}/branches")
    public ResponseEntity<Branch> addBranchToFranchise(
            @PathVariable Long franchiseId,
            @RequestBody @Valid Branch branch) {

        Branch newBranch = franchiseService.addBranchToFranchise(franchiseId, branch);
        return ResponseEntity.ok(newBranch);
    }

    @GetMapping("/{franchiseId}/top-products")
    public ResponseEntity<List<TopProductDTO>> getTopProductsByStockInEachBranch(@PathVariable Long franchiseId) {
        List<Product> topProducts = franchiseService.getTopProductsByStockInEachBranch(franchiseId);
        List<TopProductDTO> result = topProducts.stream()
                .map(product -> new TopProductDTO(product.getName(), product.getStock(), product.getBranch().getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FranchiseResponseDTO> updateFranchiseName(
            @PathVariable Long id,
            @RequestBody @Valid FranchiseUpdateDTO franchiseUpdateDTO) {

        FranchiseResponseDTO updatedFranchise = franchiseService.updateFranchiseName(id, franchiseUpdateDTO.getName());
        return new ResponseEntity<>(updatedFranchise, HttpStatus.OK);
    }
}