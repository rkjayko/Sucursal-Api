package com.example.franchiseapi.services;

import com.example.franchiseapi.dto.ProductRequestDTO;
import com.example.franchiseapi.dto.ProductResponseDTO;
import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;
import com.example.franchiseapi.mapper.ProductMapper;
import com.example.franchiseapi.repository.BranchRepository;
import com.example.franchiseapi.repository.ProductRepository;
import com.example.franchiseapi.util.BranchValidator;
import com.example.franchiseapi.util.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;
    private final BranchValidator branchValidator;

    @Autowired
    public IProductService(ProductRepository productRepository, BranchRepository branchRepository,
                           ProductMapper productMapper, ProductValidator productValidator, BranchValidator branchValidator) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.productMapper = productMapper;
        this.productValidator = productValidator;
        this.branchValidator = branchValidator;
    }

    @Override
    public void deleteProduct(Long branchId, Long productId) {
        Branch branch = branchValidator.validateBranchExists(branchId);
        Product product = productValidator.validateProductInBranch(branch, productId);
        branch.getProducts().remove(product);
        branchRepository.save(branch);
    }

    @Override
    public ProductResponseDTO addProductToBranch(Long branchId, ProductRequestDTO productRequestDTO) {
        Branch branch = branchValidator.validateBranchExists(branchId);
        productValidator.validateProductNameUnique(productRequestDTO.getName(), branchId);
        Product product = productMapper.toEntity(productRequestDTO);
        product.setBranch(branch);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public Product updateProductStock(Long productId, Integer newStock) {
        Product product = productValidator.validateProductExists(productId);
        product.setStock(newStock);
        return productRepository.save(product);
    }

    @Override
    public ProductResponseDTO updateProductName(Long productId, Long branchId, String newName) {

        Branch branch = branchValidator.validateBranchExists(branchId);
        Product product = productValidator.validateProductExists(productId);
        productValidator.validateProductInBranch(branch, productId);
        product.setName(newName);
        Product updatedProduct = productRepository.save(product);

        return new ProductResponseDTO(updatedProduct.getName(), product.getStock(), updatedProduct.getBranch().getId());
    }
}