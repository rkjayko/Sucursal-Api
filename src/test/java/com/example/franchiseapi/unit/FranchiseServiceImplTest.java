package com.example.franchiseapi.unit;

import com.example.franchiseapi.dto.response.FranchiseResponse;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.mapper.FranchiseMapper;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.repository.FranchiseRepository;
import com.example.franchiseapi.services.FranchiseServiceImpl;
import com.example.franchiseapi.util.BranchValidator;
import com.example.franchiseapi.util.FranchiseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FranchiseServiceImplTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseMapper franchiseMapper;

    @Mock
    private FranchiseValidator franchiseValidator;

    @Mock
    private BranchValidator branchValidator;

    @InjectMocks
    private FranchiseServiceImpl franchiseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFranchise() {
        com.example.franchiseapi.dto.request.Franchise requestDTO = new com.example.franchiseapi.dto.request.Franchise();
        requestDTO.setName("NewFranchise");
        Franchise franchise = new Franchise();
        FranchiseResponse responseDTO = new FranchiseResponse();

        doNothing().when(franchiseValidator).validateFranchiseNotExists(requestDTO.getName());
        when(franchiseMapper.toEntity(requestDTO)).thenReturn(franchise);
        when(franchiseRepository.save(franchise)).thenReturn(franchise);
        when(franchiseMapper.toResponseDTO(franchise)).thenReturn(responseDTO);

        FranchiseResponse result = franchiseService.addFranchise(requestDTO);

        assertEquals(responseDTO, result);
        verify(franchiseRepository).save(franchise);
    }

    @Test
    public void testAddBranchToFranchise() {
        Long franchiseId = 1L;
        Branch branch = new Branch();
        Franchise franchise = new Franchise();

        when(franchiseValidator.validateFranchiseExists(franchiseId)).thenReturn(franchise);
        doNothing().when(branchValidator).validateBranchNameUnique(branch.getName(), franchiseId);
        when(branchRepository.save(branch)).thenReturn(branch);

        Branch result = franchiseService.addBranchToFranchise(franchiseId, branch);

        assertEquals(branch, result);
        verify(branchRepository).save(branch);
    }

    @Test
    public void testGetTopProductsByStockInEachBranch() {
        Long franchiseId = 1L;
        List<Product> products = Collections.singletonList(new Product());

        when(branchRepository.findTopProductsByStockInEachBranch(franchiseId)).thenReturn(products);

        List<Product> result = franchiseService.getTopProductsByStockInEachBranch(franchiseId);

        assertEquals(products, result);
    }

    @Test
    public void testUpdateFranchiseName() {
        Long franchiseId = 1L;
        String newName = "UpdatedFranchise";
        Franchise franchise = new Franchise();
        FranchiseResponse responseDTO = new FranchiseResponse();

        when(franchiseValidator.validateFranchiseExists(franchiseId)).thenReturn(franchise);
        doNothing().when(franchiseValidator).validateFranchiseNotExists(newName);
        when(franchiseRepository.save(franchise)).thenReturn(franchise);
        when(franchiseMapper.toResponseDTO(franchise)).thenReturn(responseDTO);

        FranchiseResponse result = franchiseService.updateFranchiseName(franchiseId, newName);

        assertEquals(responseDTO, result);
        verify(franchiseRepository).save(franchise);
    }
}
