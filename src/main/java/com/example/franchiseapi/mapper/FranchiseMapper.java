package com.example.franchiseapi.mapper;

import com.example.franchiseapi.dto.response.FranchiseResponse;
import com.example.franchiseapi.entity.Franchise;
import org.springframework.stereotype.Component;

@Component
public class FranchiseMapper {

    public Franchise toEntity(com.example.franchiseapi.dto.request.Franchise dto) {
        Franchise franchise = new Franchise();
        franchise.setName(dto.getName());
        return franchise;
    }

    public FranchiseResponse toResponseDTO(Franchise franchise) {
        return new FranchiseResponse(franchise.getId(), franchise.getName());
    }
}
