package com.example.franchiseapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopProductDTO {

    private String productName;
    private Integer stock;
    private String branchName;
}
