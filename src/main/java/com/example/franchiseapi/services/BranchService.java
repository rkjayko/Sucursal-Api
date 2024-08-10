package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.BranchResponseDTO;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.util.BranchValidator;
import com.example.franchiseapi.util.FranchiseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchValidator branchValidator;
    private final FranchiseValidator franchiseValidator;

    @Autowired
    public BranchService(BranchRepository branchRepository, BranchValidator branchValidator, FranchiseValidator franchiseValidator) {
        this.branchRepository = branchRepository;
        this.branchValidator = branchValidator;
        this.franchiseValidator = franchiseValidator;
    }

    public BranchResponseDTO updateBranchName(Long branchId, Long franchiseId, String newName) {
        log.info("Attempting to update branch name with ID : {}", branchId);

        franchiseValidator.validateFranchiseExists(franchiseId);

        return Optional.of(branchId)
                .map(branchValidator::validateBranchExists)
                .map(branch -> {
                    branchValidator.validateBranchBelongsToFranchise(branch, franchiseId);
                    branchValidator.validateBranchNameUnique(newName, franchiseId);
                    branch.setName(newName);
                    return branchRepository.save(branch);
                })
                .map(updatedBranch -> {
                    log.info("Branch updated successfully with ID: {}", updatedBranch.getId());
                    return new BranchResponseDTO(updatedBranch.getId(), updatedBranch.getName(), updatedBranch.getFranchise().getId());
                })
                .orElseThrow(() -> new RuntimeException("Branch update failed"));
    }
}