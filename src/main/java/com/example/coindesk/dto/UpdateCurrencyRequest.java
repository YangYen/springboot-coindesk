package com.example.coindesk.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateCurrencyRequest {

    @NotBlank(message = "Currency code cannot be empty")
    private String code;

    @NotBlank(message = "Chinese name cannot be empty")
    @Size(min = 1, max = 50, message = "Chinese name must be between 1 and 50 characters")
    private String chineseName;
}