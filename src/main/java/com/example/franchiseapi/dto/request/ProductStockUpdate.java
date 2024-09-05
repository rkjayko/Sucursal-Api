package com.example.franchiseapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ProductStockUpdate {

    @NotNull(message = "Stock cannot be null")
    @Min(value = 1, message = "Stock must be greater than 0")
    private Integer stock;
}