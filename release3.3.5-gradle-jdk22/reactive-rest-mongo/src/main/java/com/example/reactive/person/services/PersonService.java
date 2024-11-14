package com.example.reactive.person.services;

import com.example.reactive.person.dto.PersonDTO;
import com.example.reactive.person.entity.Person;
import com.example.reactive.person.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class PersonService {

    PersonRepository repository;
    ModelMapper modelMapper;

    public PersonService(PersonRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public Mono<Page<PersonDTO>> getPersons(Pageable pageable) {
        return repository.findAllBy(pageable)
                .map(map -> modelMapper.map(map, PersonDTO.class))
                .collectList()
                .zipWith(repository.count())
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    public Mono<PersonDTO> getPersonById(String uuid) {
        return repository.findById(uuid)
                .map(map -> modelMapper.map(map, PersonDTO.class));
    }

    public Mono<PersonDTO> createPerson(PersonDTO dto) {
        return repository.save(modelMapper.map(dto, Person.class))
                .map(map -> modelMapper.map(map, PersonDTO.class));
    }

    public Mono<Void> deletePerson(String uuid) {
        return repository.deleteById(uuid);
    }

    public Mono<PersonDTO> updatePerson(String uuid, PersonDTO dto) {
        var newPerson = modelMapper.map(dto, Person.class);
        return repository.findById(uuid)
                .mapNotNull(person -> newPerson.withUuid(person.getUuid()))
                .mapNotNull(person -> repository.save(person))
                .mapNotNull(map -> modelMapper.map(map, PersonDTO.class));
    }
}
