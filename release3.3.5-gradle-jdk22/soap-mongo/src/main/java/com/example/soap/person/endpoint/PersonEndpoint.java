package com.example.soap.person.endpoint;

import com.example.soap.person.services.PersonService;
import localhost._8080.springsoap.CreatedPersonsRequest;
import localhost._8080.springsoap.CreatedPersonsResponse;
import localhost._8080.springsoap.GetPersonsRequest;
import localhost._8080.springsoap.GetPersonsResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PersonEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8080/springsoap";

    private final PersonService service;

    public PersonEndpoint(PersonService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonsRequest")
    @ResponsePayload
    public GetPersonsResponse getPersons(@RequestPayload GetPersonsRequest request) {
        var response = new GetPersonsResponse();
        response.getPersons().addAll(service.getPersons(request.getFilters()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createdPersonsRequest")
    @ResponsePayload
    public CreatedPersonsResponse createdPersons(@RequestPayload CreatedPersonsRequest request) {
        var response = new CreatedPersonsResponse();
        response.getPersons().addAll(service.createdPersons(request.getPersons()));

        return response;
    }
}
