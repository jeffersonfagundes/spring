package br.com.rest.excel.data.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
public class PersonDTO {
        String name;
        String document;
        String gender;
        LocalDate dateOfBirth;
        String fullAddress;
}
