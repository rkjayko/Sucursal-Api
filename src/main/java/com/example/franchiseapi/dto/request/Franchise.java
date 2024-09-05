package com.example.franchiseapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Franchise {

    @NotBlank(message = "Franchise name cannot be blank")
    private String name;

}
