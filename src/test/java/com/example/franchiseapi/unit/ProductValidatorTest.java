package com.example.franchiseapi.unit;

import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.exception.CustomException;
import com.example.franchiseapi.repository.ProductRepository;
import com.example.franchiseapi.util.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductValidator productValidator;

    @Mock
    private Branch branch;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateProductInBranchThrowsExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(branch.getProducts()).thenReturn(Collections.emptyList());

        CustomException.ProductIdNotFoundException exception = assertThrows(
                CustomException.ProductIdNotFoundException.class,
                () -> productValidator.validateProductInBranch(branch, productId)
        );

        assertEquals("Product with ID : 1 not found", exception.getMessage());
    }

    @Test
    public void testValidateProductInBranchReturnsProductWhenFound() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(branch.getProducts()).thenReturn(Collections.singletonList(product));

        Product result = productValidator.validateProductInBranch(branch, productId);

        assertEquals(product, result);
    }

    @Test
    public void testValidateProductExistsThrowsExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        CustomException.ProductIdNotFoundException exception = assertThrows(
                CustomException.ProductIdNotFoundException.class,
                () -> productValidator.validateProductExists(productId)
        );

        assertEquals("Product with ID : 1 not found", exception.getMessage());
    }

    @Test
    public void testValidateProductExistsReturnsProductWhenFound() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productValidator.validateProductExists(productId);

        assertEquals(product, result);
    }

    @Test
    public void testValidateProductNameUniqueThrowsExceptionWhenProductNameExists() {
        String productName = "ProductCopy";
        Long branchId = 1L;
        when(productRepository.existsByNameAndBranchId(productName, branchId)).thenReturn(true);

        CustomException.ProductAlreadyExistsException exception = assertThrows(
                CustomException.ProductAlreadyExistsException.class,
                () -> productValidator.validateProductNameUnique(productName, branchId)
        );

        assertEquals("Product with name : ProductCopy already exists", exception.getMessage());
    }

    @Test
    public void testValidateProductNameUniqueDoesNotThrowExceptionWhenProductNameDoesNotExist() {
        String productName = "NewProduct";
        Long branchId = 1L;
        when(productRepository.existsByNameAndBranchId(productName, branchId)).thenReturn(false);

        assertDoesNotThrow(() -> productValidator.validateProductNameUnique(productName, branchId));
    }
}