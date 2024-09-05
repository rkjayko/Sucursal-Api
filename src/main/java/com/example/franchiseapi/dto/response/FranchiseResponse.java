package com.example.franchiseapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class FranchiseResponse {

    private Long id;
    private String name;

}
