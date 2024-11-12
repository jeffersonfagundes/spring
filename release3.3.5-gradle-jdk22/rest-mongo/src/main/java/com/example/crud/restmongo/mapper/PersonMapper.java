package com.example.crud.restmongo.mapper;

import com.example.crud.restmongo.dto.PersonDTO;
import com.example.crud.restmongo.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {UUID.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

    PersonDTO personToPersonDTO(Person person);
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "gender", expression = "java(dto.gender().toLowerCase())")
    Person personDTOToPerson(PersonDTO dto);
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "gender", expression = "java(dto.gender().toLowerCase())")
    void mergePerson(PersonDTO dto, @MappingTarget Person person);
}

