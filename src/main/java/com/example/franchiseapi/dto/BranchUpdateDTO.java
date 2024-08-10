package com.example.franchiseapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchUpdateDTO {

    @NotBlank(message = "New name cannot be blank")
    private String name;
}
