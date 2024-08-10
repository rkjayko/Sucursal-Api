package com.example.franchiseapi.unit;

import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.util.BranchValidator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BranchValidatorTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchValidator branchValidator;

    public BranchValidatorTest() {
    }

    @Test
    public void testValidateBranchExistsThrowsExceptionWhenBranchNotFound() {

        Long branchId = 1L;
        when(branchRepository.findById(branchId)).thenReturn(Optional.empty());

        CustomException.BranchIdNotFoundException exception = assertThrows(
                CustomException.BranchIdNotFoundException.class,
                () -> branchValidator.validateBranchExists(branchId)
        );
        assertEquals("Branch with ID : 1 not found", exception.getMessage());
    }

    @Test
    public void testValidateBranchExistsReturnsBranchWhenBranchFound() {
        Long branchId = 1L;
        Branch branch = new Branch();
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(branch));

        Branch result = branchValidator.validateBranchExists(branchId);
        assertNotNull(result);
    }

    @Test
    public void testValidateBranchBelongsToFranchiseThrowsExceptionWhenBranchDoesNotBelongToFranchise() {

        Branch branch = new Branch();
        branch.setFranchise(new Franchise());
        branch.getFranchise().setId(2L);

        Long franchiseId = 1L;

        CustomException.BrandBelongToFranchiseException exception = assertThrows(
                CustomException.BrandBelongToFranchiseException.class,
                () -> branchValidator.validateBranchBelongsToFranchise(branch, franchiseId)
        );
        assertNotNull(exception);
    }

    @Test
    public void testValidateBranchBelongsToFranchiseDoesNotThrowExceptionWhenBranchBelongsToFranchise() {

        Branch branch = new Branch();
        Franchise franchise = new Franchise();
        franchise.setId(1L);
        branch.setFranchise(franchise);

        Long franchiseId = 1L;

        assertDoesNotThrow(() -> branchValidator.validateBranchBelongsToFranchise(branch, franchiseId));
    }

    
    @Test
    public void testValidateBranchNameUnique_DoesNotThrowException_WhenBranchNameDoesNotExist() {

        String branchName = "NewBranch";
        Long franchiseId = 1L;
        when(branchRepository.existsByNameAndFranchiseId(branchName, franchiseId)).thenReturn(false);

        assertDoesNotThrow(() -> branchValidator.validateBranchNameUnique(branchName, franchiseId));
    }
}
