package com.example.crud.restmongo.services;

import com.example.crud.restmongo.dto.GenderDataDTO;
import com.example.crud.restmongo.dto.PersonDTO;
import com.example.crud.restmongo.mapper.PersonMapper;
import com.example.crud.restmongo.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
public class PersonService {

    PersonRepository repository;
    PersonMapper mapper;

    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<PersonDTO> getPersons(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::personToPersonDTO);
    }

    public PersonDTO getPersonById(String uuid) {
        return mapper.personToPersonDTO(repository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Person não encontrada.")));
    }

    public PersonDTO createPerson(PersonDTO dto) {
        var person = mapper.personDTOToPerson(dto);
        return mapper.personToPersonDTO(repository.save(person));
    }

    public void deletePerson(String uuid) {
        repository.deleteById(uuid);
    }

    public PersonDTO updatePerson(String uuid, PersonDTO dto) {
        var person = repository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pessoa não encontrada."));
        mapper.mergePerson(dto, person);
        return mapper.personToPersonDTO(repository.save(person));
    }

    public List<GenderDataDTO> getDataPersonOfGender() {
        return repository.calculateTheDataOfGenresIn();
    }
}
