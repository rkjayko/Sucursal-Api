package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.FranchiseRequestDTO;
import com.example.franchiseapi.dto.FranchiseResponseDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.mapper.FranchiseMapper;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.repository.FranchiseRepository;
import com.example.franchiseapi.util.BranchValidator;
import com.example.franchiseapi.util.FranchiseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IFranchiseService implements FranchiseServiceInterface {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    private final FranchiseMapper franchiseMapper;
    private final FranchiseValidator franchiseValidator;
    private final BranchValidator branchValidator;

    @Autowired
    public IFranchiseService(FranchiseRepository franchiseRepository, BranchRepository branchRepository,
                             FranchiseMapper franchiseMapper, FranchiseValidator franchiseValidator, BranchValidator branchValidator) {
        this.franchiseRepository = franchiseRepository;
        this.branchRepository = branchRepository;
        this.franchiseMapper = franchiseMapper;
        this.franchiseValidator = franchiseValidator;
        this.branchValidator = branchValidator;
    }

    @Override
    public FranchiseResponseDTO addFranchise(FranchiseRequestDTO franchiseRequestDTO) {
        log.info("Add new Franchise : {}", franchiseRequestDTO.getName());
        franchiseValidator.validateFranchiseNotExists(franchiseRequestDTO.getName());

        Franchise franchise = franchiseMapper.toEntity(franchiseRequestDTO);
        Franchise savedFranchise = franchiseRepository.save(franchise);
        return franchiseMapper.toResponseDTO(savedFranchise);
    }

    @Override
    public Branch addBranchToFranchise(Long franchiseId, Branch branch) {
        log.info("Add new Brand to Franchise : {}", branch.getName());
        Franchise franchise = franchiseValidator.validateFranchiseExists(franchiseId);
        branchValidator.validateBranchNameUnique(branch.getName(), franchiseId);
        branch.setFranchise(franchise);
        return branchRepository.save(branch);
    }

    @Override
    public List<Product> getTopProductsByStockInEachBranch(Long franchiseId) {
        return branchRepository.findTopProductsByStockInEachBranch(franchiseId);
    }

    @Override
    public FranchiseResponseDTO updateFranchiseName(Long id, String newName) {
        Franchise franchise = franchiseValidator.validateFranchiseExists(id);
        franchiseValidator.validateFranchiseNotExists(newName);

        franchise.setName(newName);
        Franchise updatedFranchise = franchiseRepository.save(franchise);
        return franchiseMapper.toResponseDTO(updatedFranchise);
    }
}