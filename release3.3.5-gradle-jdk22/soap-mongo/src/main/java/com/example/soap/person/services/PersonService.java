package com.example.soap.person.services;

import com.example.soap.person.mapper.PersonMapper;
import com.example.soap.person.repository.PersonRepository;
import localhost._8080.springsoap.FilterDTO;
import localhost._8080.springsoap.PersonIn;
import localhost._8080.springsoap.PersonOut;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class PersonService {

    PersonRepository repository;
    PersonMapper mapper;

    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PersonOut> getPersons(FilterDTO dto) {
        var matchCriteria = new HashMap<String, Pattern>();
        if (Objects.nonNull(dto.getUuid()))
            matchCriteria.put("uuid", Pattern.compile(dto.getUuid()));
        if (Objects.nonNull(dto.getName()))
            matchCriteria.put("name", Pattern.compile(dto.getName()));
        if (Objects.nonNull(dto.getGender()))
            matchCriteria.put("gender", Pattern.compile(dto.getGender().value().toLowerCase()));
        if (Objects.nonNull(dto.getDocument()))
            matchCriteria.put("document", Pattern.compile(dto.getDocument()));

        return repository.findBy(matchCriteria)
                .parallelStream()
                .map(mapper::personToPersonOut)
                .toList();
    }

    public Collection<? extends PersonOut> createdPersons(List<PersonIn> persons) {
        return persons.parallelStream()
                .map(mapper::personInToPerson)
                .map(repository::save)
                .map(mapper::personToPersonOut)
                .toList();
    }
}
