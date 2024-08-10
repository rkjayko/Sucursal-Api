package com.example.franchiseapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseDTO {

    private Long id;
    private String name;
    private Long franchiseId;

}