package com.example.franchiseapi.unit;

import com.example.franchiseapi.entity.Franchise;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.FranchiseRepository;
import com.example.franchiseapi.util.FranchiseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FranchiseValidatorTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseValidator franchiseValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateFranchiseExistsThrowsExceptionWhenFranchiseNotFound() {
        Long franchiseId = 1L;
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.empty());

        CustomException.FranchiseIdNotFoundException exception = assertThrows(
                CustomException.FranchiseIdNotFoundException.class,
                () -> franchiseValidator.validateFranchiseExists(franchiseId)
        );

        assertEquals("Franchise with ID : 1 not found", exception.getMessage());
    }

    @Test
    public void testValidateFranchiseExistsReturnsFranchiseWhenFound() {
        Long franchiseId = 1L;
        Franchise franchise = new Franchise();
        when(franchiseRepository.findById(franchiseId)).thenReturn(Optional.of(franchise));

        Franchise result = franchiseValidator.validateFranchiseExists(franchiseId);

        assertEquals(franchise, result);
    }

    @Test
    public void testValidateFranchiseNotExistsThrowsExceptionWhenFranchiseExists() {
        String franchiseName = "ExistingFranchise";
        when(franchiseRepository.existsByName(franchiseName)).thenReturn(true);

        CustomException.FranchiseAlreadyExistsException exception = assertThrows(
                CustomException.FranchiseAlreadyExistsException.class,
                () -> franchiseValidator.validateFranchiseNotExists(franchiseName)
        );

        assertEquals("Franchise with name : ExistingFranchise already exists", exception.getMessage());
    }

    @Test
    public void testValidateFranchiseNotExistsDoesNotThrowExceptionWhenFranchiseDoesNotExist() {
        String franchiseName = "NewFranchise";
        when(franchiseRepository.existsByName(franchiseName)).thenReturn(false);

        assertDoesNotThrow(() -> franchiseValidator.validateFranchiseNotExists(franchiseName));
    }
}
