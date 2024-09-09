package com.cloudbees.assessment.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class TrainDto {

    @Size(min = 1, max = 20)
    private String from;

    @Size(min = 1, max = 20)
    private String to;

    @Min(0)
    @Max(10000)
    private double price;
}
