package com.example.franchiseapi.util;

import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.FranchiseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FranchiseValidator {

    private final FranchiseRepository franchiseRepository;

    @Autowired
    public FranchiseValidator(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Franchise validateFranchiseExists(Long franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> {
                    log.error("Franchise not found with ID: {}", franchiseId);
                    return new CustomException.FranchiseIdNotFoundException(franchiseId);
                });
    }

    public void validateFranchiseNotExists(String name) {
        if (franchiseRepository.existsByName(name)) {
            log.error("Franchise not found with Name: {}", name);
            throw new CustomException.FranchiseAlreadyExistsException(name);
        }
    }
}