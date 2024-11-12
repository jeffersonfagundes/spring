package com.example.crud.restmongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenderDataDTO {
    String genderType;
    Integer total;
    Double medianAge;
}
