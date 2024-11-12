package com.example.crud.restmongo.controller;

import com.example.crud.restmongo.dto.GenderDataDTO;
import com.example.crud.restmongo.dto.PersonDTO;
import com.example.crud.restmongo.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PersonDTO> getAllPerson(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size) {
        return service.getPersons(PageRequest.of(page, size));
    }

    @GetMapping("/{uuid}")
    public PersonDTO getPersonById(@PathVariable("uuid") String uuid) {
        return service.getPersonById(uuid);
    }

    @GetMapping("/report/gender")
    public List<GenderDataDTO> getDataPersonOfGender() {
        return service.getDataPersonOfGender();
    }

    @PostMapping
    public PersonDTO addPerson(@RequestBody @Valid PersonDTO dto) {
        return service.createPerson(dto);
    }

    @DeleteMapping("/{uuid}")
    public void deletePerson(@PathVariable("uuid") String uuid) {
        service.deletePerson(uuid);
    }

    @PutMapping("/{uuid}")
    public PersonDTO updatePerson(@PathVariable("uuid") String uuid, @RequestBody @Valid PersonDTO dto) {
        return service.updatePerson(uuid, dto);
    }

    @PatchMapping("/{uuid}")
    public PersonDTO updatePartialPerson(@PathVariable("uuid") String uuid, @RequestBody PersonDTO dto) {
        return service.updatePerson(uuid, dto);
    }
}
