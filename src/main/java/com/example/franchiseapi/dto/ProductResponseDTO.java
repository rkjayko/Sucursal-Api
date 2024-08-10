package com.example.franchiseapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDTO {

    public ProductResponseDTO(String name, Integer stock) {
        this.name = name;
        this.stock = stock;
    }

    private String name;

    private Integer stock;

    @JsonIgnore
    private Long branchId;
}
