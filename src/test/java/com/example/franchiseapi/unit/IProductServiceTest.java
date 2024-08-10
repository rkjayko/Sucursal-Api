package com.example.franchiseapi.unit;

import com.example.franchiseapi.dto.ProductRequestDTO;
import com.example.franchiseapi.dto.ProductResponseDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.mapper.ProductMapper;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.repository.ProductRepository;
import com.example.franchiseapi.services.IProductService;
import com.example.franchiseapi.util.BranchValidator;
import com.example.franchiseapi.util.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class IProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private BranchValidator branchValidator;

    @InjectMocks
    private IProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteProduct() {
        Long branchId = 1L;
        Long productId = 2L;
        Branch branch = new Branch();
        Product product = new Product();
        product.setId(productId);

        List<Product> products = new ArrayList<>();
        products.add(product);
        branch.setProducts(products);

        when(branchValidator.validateBranchExists(branchId)).thenReturn(branch);
        when(productValidator.validateProductInBranch(branch, productId)).thenReturn(product);

        when(branchRepository.save(branch)).thenReturn(branch);

        productService.deleteProduct(branchId, productId);

        assertFalse(branch.getProducts().contains(product));
        verify(branchRepository).save(branch);
    }

    @Test
    public void testAddProductToBranch() {
        Long branchId = 1L;
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("NewProduct");
        Branch branch = new Branch();
        Product product = new Product();
        ProductResponseDTO responseDTO = new ProductResponseDTO();

        when(branchValidator.validateBranchExists(branchId)).thenReturn(branch);
        doNothing().when(productValidator).validateProductNameUnique(requestDTO.getName(), branchId);
        when(productMapper.toEntity(requestDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(responseDTO);

        ProductResponseDTO result = productService.addProductToBranch(branchId, requestDTO);

        assertEquals(responseDTO, result);
        verify(productRepository).save(product);
    }

    @Test
    public void testUpdateProductStock() {
        Long productId = 1L;
        Integer newStock = 10;
        Product product = new Product();
        product.setId(productId);

        when(productValidator.validateProductExists(productId)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProductStock(productId, newStock);

        assertEquals(newStock, updatedProduct.getStock());
        verify(productRepository).save(product);
    }

    @Test
    public void testUpdateProductName() {
        Long productId = 1L;
        Long branchId = 2L;
        String newName = "UpdatedProduct";
        Branch branch = new Branch();
        branch.setId(branchId);
        Product product = new Product();
        product.setId(productId);
        product.setBranch(branch);
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setName(newName);

        when(branchValidator.validateBranchExists(branchId)).thenReturn(branch);
        when(productValidator.validateProductExists(productId)).thenReturn(product);
        when(productValidator.validateProductInBranch(branch, productId)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDTO(product)).thenReturn(responseDTO);

        productService.updateProductName(productId, branchId, newName);
        verify(productRepository).save(product);
    }
}
