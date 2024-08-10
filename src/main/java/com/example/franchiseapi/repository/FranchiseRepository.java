package com.example.franchiseapi.repository;

import com.example.franchiseapi.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {

    boolean existsByName(String name);
}
