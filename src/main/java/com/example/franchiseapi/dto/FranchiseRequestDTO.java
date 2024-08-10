package com.example.franchiseapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class FranchiseRequestDTO {

    @NotBlank(message = "Franchise name cannot be blank")
    private String name;

}
