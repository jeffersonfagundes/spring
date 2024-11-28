package com.example.soap.person.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Person")
public class Person {
    @Id String uuid;
    String name;
    String document;
    String gender;
    LocalDate dateOfBirth;
    String fullAddress;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime lastModifiedDate;
}
