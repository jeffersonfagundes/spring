package com.example.crud.restmongo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonDTO (
        @NotBlank String name,
        @NotBlank @Size(min = 11, max = 11) String document,
        @Pattern(regexp = "masculino|feminino|trans", flags = Pattern.Flag.CASE_INSENSITIVE) String gender,
        @NotNull LocalDate dateOfBirth,
        String fullAddress,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) LocalDateTime createdDate,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)LocalDateTime lastModifiedDate){
}