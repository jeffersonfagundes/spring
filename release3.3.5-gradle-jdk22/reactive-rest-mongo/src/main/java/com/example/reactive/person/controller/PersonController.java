package com.example.reactive.person.controller;

import com.example.reactive.person.dto.PersonDTO;
import com.example.reactive.person.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/person")
public class PersonController {

    PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Mono<Page<PersonDTO>> getAllPerson(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return service.getPersons(PageRequest.of(page, size));
    }

    @GetMapping("/{uuid}")
    public Mono<PersonDTO> getPersonById(@PathVariable("uuid") String uuid) {
        return service.getPersonById(uuid)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Person não encontrada.")));
    }

    @PostMapping
    public Mono<PersonDTO> addPerson(@RequestBody @Valid PersonDTO dto) {
        return service.createPerson(dto);
    }

    @DeleteMapping("/{uuid}")
    public Mono<Void> deletePerson(@PathVariable("uuid") String uuid) {
        return service.deletePerson(uuid)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Person não encontrada.")));
    }

    @PutMapping("/{uuid}")
    public Mono<PersonDTO> updatePerson(@PathVariable("uuid") String uuid, @RequestBody @Valid PersonDTO dto) {
        return service.updatePerson(uuid, dto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Person não encontrada.")));
    }
}
