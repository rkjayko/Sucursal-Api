package com.example.franchiseapi.repository;

import com.example.franchiseapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndBranchId(String name, Long branchId);
}
