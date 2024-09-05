package com.example.franchiseapi.util;

import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchValidator {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchValidator(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch validateBranchExists(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> {
                    return new CustomException.BranchIdNotFoundException(branchId);
                });
    }

    public void validateBranchBelongsToFranchise(Branch branch, Long franchiseId) {
        if (!branch.getFranchise().getId().equals(franchiseId)) {
            throw new CustomException.BrandBelongToFranchiseException();
        }
    }

    public void validateBranchNameUnique(String newName, Long franchiseId) {
        if (branchRepository.existsByNameAndFranchiseId(newName, franchiseId)) {
            throw new CustomException.BranchAlreadyExistsException(newName);
        }
    }
}