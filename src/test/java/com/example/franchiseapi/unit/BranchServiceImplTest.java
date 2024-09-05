package com.example.franchiseapi.unit;

import com.example.franchiseapi.dto.response.BranchDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.services.BranchServiceImpl;
import com.example.franchiseapi.util.BranchValidator;
import com.example.franchiseapi.util.FranchiseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private BranchValidator branchValidator;

    @Mock
    private FranchiseValidator franchiseValidator;

    @InjectMocks
    private BranchServiceImpl branchServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateBranchName() {
        Long branchId = 1L;
        Long franchiseId = 1L;
        String newName = "NewBranchName";

        Branch branch = new Branch();
        branch.setId(branchId);
        branch.setName("OldBranchName");
        Franchise franchise = new Franchise();
        franchise.setId(franchiseId);
        branch.setFranchise(franchise);

        when(franchiseValidator.validateFranchiseExists(franchiseId)).thenReturn(franchise);
        when(branchValidator.validateBranchExists(branchId)).thenReturn(branch);
        doNothing().when(branchValidator).validateBranchBelongsToFranchise(branch, franchiseId);
        doNothing().when(branchValidator).validateBranchNameUnique(newName, franchiseId);
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);

        BranchDTO response = branchServiceImpl.updateBranchName(branchId, franchiseId, newName);

        assertEquals(branchId, response.getId());
        assertEquals(newName, response.getName());
        assertEquals(franchiseId, response.getFranchiseId());
        verify(branchRepository).save(branch);
        verify(branchValidator).validateBranchNameUnique(newName, franchiseId);
    }

    @Test
    public void testUpdateBranchNameThrowsExceptionWhenFranchiseNotFound() {
        Long branchId = 1L;
        Long franchiseId = 1L;
        String newName = "NewBranchName";

        when(franchiseValidator.validateFranchiseExists(franchiseId))
                .thenThrow(new CustomException.FranchiseIdNotFoundException(franchiseId));

        CustomException.FranchiseIdNotFoundException exception = assertThrows(
                CustomException.FranchiseIdNotFoundException.class,
                () -> branchServiceImpl.updateBranchName(branchId, franchiseId, newName)
        );

        assertEquals("Franchise with ID : 1 not found", exception.getMessage());
        verifyNoInteractions(branchValidator, branchRepository);
    }

    @Test
    public void testUpdateBranchNameThrowsExceptionWhenBranchNotFound() {
        Long branchId = 1L;
        Long franchiseId = 1L;
        String newName = "NewBranchName";

        when(franchiseValidator.validateFranchiseExists(franchiseId)).thenReturn(new Franchise());
        when(branchValidator.validateBranchExists(branchId))
                .thenThrow(new CustomException.BranchIdNotFoundException(branchId));

        CustomException.BranchIdNotFoundException exception = assertThrows(
                CustomException.BranchIdNotFoundException.class,
                () -> branchServiceImpl.updateBranchName(branchId, franchiseId, newName)
        );

        assertEquals("Branch with ID : 1 not found", exception.getMessage());
        verifyNoInteractions(branchRepository);
    }
}