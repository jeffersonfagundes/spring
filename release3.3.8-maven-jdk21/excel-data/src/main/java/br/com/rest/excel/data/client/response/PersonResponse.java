package br.com.rest.excel.data.client.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PersonResponse (String name, String document, LocalDate dateOfBirth, String fullAddress, LocalDateTime createdDate, LocalDateTime lastModifiedDate){
}
