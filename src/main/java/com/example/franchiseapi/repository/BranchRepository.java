package com.example.franchiseapi.repository;

import com.example.franchiseapi.entity.Branch;
import com.example.franchiseapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("SELECT p FROM Product p WHERE p.branch.franchise.id = :franchiseId AND p.stock = (SELECT MAX(p2.stock) FROM Product p2 WHERE p2.branch.id = p.branch.id)")
    List<Product> findTopProductsByStockInEachBranch(@Param("franchiseId") Long franchiseId);

    boolean existsByNameAndFranchiseId(String name, Long franchiseId);

}