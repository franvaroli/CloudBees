package com.cloudbees.assessment.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UserDto {

    @Size(min = 1, max = 20)
    private String name;

    @Size(min = 1, max = 20)
    private String lastName;

    @Size(min = 1, max = 25)
    private String email;
}
