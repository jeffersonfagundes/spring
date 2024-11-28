package com.example.soap.person.mapper;

import com.example.soap.person.entity.Person;
import localhost._8080.springsoap.PersonIn;
import localhost._8080.springsoap.PersonOut;
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

    PersonOut personToPersonOut(Person person);
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "gender", expression = "java(out.getGender().toLowerCase())")
    Person personInToPerson(PersonIn out);
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "gender", expression = "java(out.getGender().toLowerCase())")
    void mergePerson(PersonOut out, @MappingTarget Person person);
}
