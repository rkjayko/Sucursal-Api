package com.example.franchiseapi.unit;

import com.example.franchiseapi.dto.FranchiseRequestDTO;
import com.example.franchiseapi.dto.FranchiseResponseDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.mapper.FranchiseMapper;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.repository.FranchiseRepository;
import com.example.franchiseapi.services.IFranchiseService;
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

public class IFranchiseServiceTest {

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
    private IFranchiseService franchiseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFranchise() {
        FranchiseRequestDTO requestDTO = new FranchiseRequestDTO();
        requestDTO.setName("NewFranchise");
        Franchise franchise = new Franchise();
        FranchiseResponseDTO responseDTO = new FranchiseResponseDTO();

        doNothing().when(franchiseValidator).validateFranchiseNotExists(requestDTO.getName());
        when(franchiseMapper.toEntity(requestDTO)).thenReturn(franchise);
        when(franchiseRepository.save(franchise)).thenReturn(franchise);
        when(franchiseMapper.toResponseDTO(franchise)).thenReturn(responseDTO);

        FranchiseResponseDTO result = franchiseService.addFranchise(requestDTO);

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
        FranchiseResponseDTO responseDTO = new FranchiseResponseDTO();

        when(franchiseValidator.validateFranchiseExists(franchiseId)).thenReturn(franchise);
        doNothing().when(franchiseValidator).validateFranchiseNotExists(newName);
        when(franchiseRepository.save(franchise)).thenReturn(franchise);
        when(franchiseMapper.toResponseDTO(franchise)).thenReturn(responseDTO);

        FranchiseResponseDTO result = franchiseService.updateFranchiseName(franchiseId, newName);

        assertEquals(responseDTO, result);
        verify(franchiseRepository).save(franchise);
    }
}
