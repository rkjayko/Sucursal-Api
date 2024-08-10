package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.FranchiseRequestDTO;
import com.example.franchiseapi.dto.FranchiseResponseDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;

import java.util.List;

public interface FranchiseServiceInterface {
    FranchiseResponseDTO addFranchise(FranchiseRequestDTO franchiseRequestDTO);

    Branch addBranchToFranchise(Long franchiseId, Branch branch);

    List<Product> getTopProductsByStockInEachBranch(Long franchiseId);

    FranchiseResponseDTO updateFranchiseName(Long id, String newName);
}