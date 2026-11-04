package com.example.app.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
// to process incoming data for a new game. e.g. the values from the form on indexView
public class FormConfig {

    @NotNull(message = "{maxIntent.notNull}")
    @Min(value = 5, message = "{maxIntent.Min}")
    @Max(value = 100, message = "{maxIntent.Max}")
    private Integer maxIntent = 10;
    
    @NotNull(message = "{numLength.notNull}")
    @Min(value = 3, message = "{numLength.Min}")
    @Max(value = 10, message = "{numLength.Max}")
    private Integer numLength = 4;   
}
