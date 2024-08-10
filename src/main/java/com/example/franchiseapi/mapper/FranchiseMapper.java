package com.example.franchiseapi.mapper;

import com.example.franchiseapi.dto.FranchiseRequestDTO;
import com.example.franchiseapi.dto.FranchiseResponseDTO;
import com.example.franchiseapi.entity.Franchise;
import org.springframework.stereotype.Component;

@Component
public class FranchiseMapper {

    public Franchise toEntity(FranchiseRequestDTO dto) {
        Franchise franchise = new Franchise();
        franchise.setName(dto.getName());
        return franchise;
    }

    public FranchiseResponseDTO toResponseDTO(Franchise franchise) {
        return new FranchiseResponseDTO(franchise.getId(), franchise.getName());
    }
}
