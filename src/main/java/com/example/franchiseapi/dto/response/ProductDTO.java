package com.example.franchiseapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private String name;
    private Integer stock;

    @JsonIgnore
    private Long branchId;

    public ProductDTO(String name, Integer stock) {
        this.name = name;
        this.stock = stock;
    }
}
