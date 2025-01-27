package br.com.rest.excel.data.client;

import br.com.rest.excel.data.client.response.PersonResponse;
import br.com.rest.excel.data.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PersonClient {

    private final RestClient restClient;

    @Autowired
    public PersonClient(RestClient.Builder builder,
                                   @Value("${services.person.uri}") String uri) {
        this.restClient = builder
                .baseUrl(uri)
                .build();
    }

    public PersonResponse criarPessoa(PersonDTO personDTO) {
        return restClient
                .post()
                .uri("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .body(personDTO)
                .retrieve()
                .body(PersonResponse.class);
    }

}
