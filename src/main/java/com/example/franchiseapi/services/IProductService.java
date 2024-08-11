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

import java.util.Optional;

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
        Optional<Branch> branchOptional = Optional.of(branchId)
                .map(branchValidator::validateBranchExists);

        branchOptional.flatMap(branch -> Optional.of(productId)
                .map(productIdOptional -> productValidator.validateProductInBranch(branch, productIdOptional))
                .map(product -> {
                    branch.getProducts().remove(product);
                    branchRepository.save(branch);
                    return branch;
                })
        ).orElseThrow(() -> new RuntimeException("Failed to delete product"));
    }

    @Override
    public ProductResponseDTO addProductToBranch(Long branchId, ProductRequestDTO productRequestDTO) {
        return Optional.of(branchId)
                .map(branchValidator::validateBranchExists)
                .map(branch -> {
                    productValidator.validateProductNameUnique(productRequestDTO.getName(), branchId);
                    Product product = productMapper.toEntity(productRequestDTO);
                    product.setBranch(branch);
                    return productRepository.save(product);
                })
                .map(productMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Failed to add product"));
    }

    
    @Override
    public Product updateProductStock(Long productId, Integer newStock) {
        return Optional.of(productId)
                .map(productValidator::validateProductExists)
                .map(product -> {
                    product.setStock(newStock);
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Failed to update product stock"));
    }

    @Override
    public ProductResponseDTO updateProductName(Long productId, Long branchId, String newName) {
        return Optional.of(branchId)
                .map(branchValidator::validateBranchExists)
                .flatMap(branch -> Optional.of(productId)
                        .map(productValidator::validateProductExists)
                        .map(product -> {
                            productValidator.validateProductInBranch(branch, productId);
                            product.setName(newName);
                            Product updatedProduct = productRepository.save(product);
                            return new ProductResponseDTO(updatedProduct.getName(), updatedProduct.getStock(), updatedProduct.getBranch().getId());
                        })
                )
                .orElseThrow(() -> new RuntimeException("Failed to update product name"));
    }
}