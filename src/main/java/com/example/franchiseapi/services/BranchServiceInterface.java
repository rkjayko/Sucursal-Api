package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.response.BranchDTO;

public interface BranchServiceInterface {

    BranchDTO updateBranchName(Long branchId, Long franchiseId, String newName);
}
