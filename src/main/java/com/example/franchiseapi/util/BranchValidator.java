package com.example.franchiseapi.util;

import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BranchValidator {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchValidator(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch validateBranchExists(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> {
                    log.error("Branch not found with ID: {}", branchId);
                    return new CustomException.BranchIdNotFoundException(branchId);
                });
    }

    public void validateBranchBelongsToFranchise(Branch branch, Long franchiseId) {
        if (!branch.getFranchise().getId().equals(franchiseId)) {
            log.error("Brand doesn't belong to franchise: {} {}", branch.getId(), franchiseId);
            throw new CustomException.BrandBelongToFranchiseException();
        }
    }

    public void validateBranchNameUnique(String newName, Long franchiseId) {
        if (branchRepository.existsByNameAndFranchiseId(newName, franchiseId)) {
            log.error("Branch with name: {} exists with franchise : {}", newName, franchiseId);
            throw new CustomException.BranchAlreadyExistsException(newName);
        }
    }
}