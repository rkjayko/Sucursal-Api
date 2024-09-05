package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.request.Franchise;
import com.example.franchiseapi.dto.response.FranchiseResponse;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;

import java.util.List;

public interface FranchiseServiceInterface {
    FranchiseResponse addFranchise(Franchise franchise);

    Branch addBranchToFranchise(Long franchiseId, Branch branch);

    List<Product> getTopProductsByStockInEachBranch(Long franchiseId);

    FranchiseResponse updateFranchiseName(Long id, String newName);
}