package com.example.reactive.person.controller;

import com.example.reactive.person.dto.PersonDTO;
import com.example.reactive.person.services.PersonService;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Component
public class PersonV2Controller {

    PersonService service;
    LocalValidatorFactoryBean validator;

    public PersonV2Controller(PersonService service, LocalValidatorFactoryBean validator) {
        this.service = service;
        this.validator = validator;
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        return service
                .getPersonById(request.pathVariable("uuid"))
                .flatMap(personDTO -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(personDTO, PersonDTO.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllPerson(ServerRequest request) {
        return service
                .getPersons(PageRequest.of(
                        Integer.parseInt(request.queryParam("page").orElse("0")),
                        Integer.parseInt(request.queryParam("size").orElse("20"))))
                .flatMap(personDTO -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(personDTO)
                );
    }

    public Mono<ServerResponse> addPerson(ServerRequest request) {
        var personDTO = request.bodyToMono(PersonDTO.class).doOnNext(this::validate);
        return personDTO
                .flatMap(dto -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.createPerson(dto), PersonDTO.class)
                );
    }

    public Mono<ServerResponse> updatePerson(ServerRequest request) {
        var personDTO = request.bodyToMono(PersonDTO.class).doOnNext(this::validate);
        return personDTO
                .flatMap(dto -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.updatePerson(request.pathVariable("uuid"), dto), PersonDTO.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletePerson(ServerRequest request) {
        return service
                .deletePerson(request.pathVariable("uuid"))
                .flatMap(_ -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private void validate(PersonDTO personDTO) {
        BindingResult errors = new BeanPropertyBindingResult(personDTO, "personDTO");
        validator.validate(personDTO, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
