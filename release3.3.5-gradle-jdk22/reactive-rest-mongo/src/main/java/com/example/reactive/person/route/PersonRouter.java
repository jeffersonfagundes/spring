package com.example.reactive.person.route;

import com.example.reactive.person.controller.PersonV2Controller;
import com.example.reactive.person.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class PersonRouter {
    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/v2/person/{uuid}",
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET,
                            beanClass = PersonV2Controller.class,
                            beanMethod = "getPersonById",
                            operation = @Operation(
                                    operationId = "getPersonByIdV2",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "OK",
                                                    content = @Content(schema = @Schema(
                                                            implementation = PersonDTO.class
                                                    ))
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "uuid", required = true,
                                                    schema = @Schema(contentSchema = String.class))
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/v2/person",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = PersonV2Controller.class,
                            beanMethod = "getAllPerson",
                            operation = @Operation(
                                    operationId = "getAllPersonV2",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "OK",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Page.class
                                                    ))
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.QUERY, name = "page",
                                                    schema = @Schema(implementation = Integer.class)),
                                            @Parameter(in = ParameterIn.QUERY, name = "size",
                                                    schema = @Schema(implementation = Integer.class))
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/v2/person",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = PersonV2Controller.class,
                            beanMethod = "addPerson",
                            operation = @Operation(
                                    operationId = "addPersonV2",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "201",
                                                    description = "CREATED",
                                                    content = @Content(schema = @Schema(
                                                            implementation = PersonDTO.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = PersonDTO.class
                                            )),
                                            required = true
                                    )

                            )


                    ),
                    @RouterOperation(
                            path = "/v2/person/{uuid}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.PUT,
                            beanClass = PersonV2Controller.class,
                            beanMethod = "updatePerson",
                            operation = @Operation(
                                    operationId = "updatePersonV2",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "OK",
                                                    content = @Content(schema = @Schema(
                                                            implementation = PersonDTO.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = PersonDTO.class
                                            )),
                                            required = true
                                    ),
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "uuid", required = true,
                                                    schema = @Schema(implementation = String.class))
                                    }

                            )


                    ),
                    @RouterOperation(
                            path = "/v2/person/{uuid}",
                            method = RequestMethod.DELETE,
                            beanClass = PersonV2Controller.class,
                            beanMethod = "deletePerson",
                            operation = @Operation(
                                    operationId = "deletePersonV2",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "OK",
                                                    content = @Content
                                            )
                                    },
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "uuid", required = true,
                                            schema = @Schema(implementation = String.class))}

                            )


                    )
            }
    )
    @Bean
    public RouterFunction<ServerResponse> route(PersonV2Controller personHandler) {
        return RouterFunctions.route()
                .GET("/v2/person/{uuid}", accept(MediaType.APPLICATION_JSON), personHandler::getPersonById)
                .GET("/v2/person", accept(MediaType.APPLICATION_JSON), personHandler::getAllPerson)
                .POST("/v2/person", accept(MediaType.APPLICATION_JSON), personHandler::addPerson)
                .PUT("/v2/person/{uuid}",accept(MediaType.APPLICATION_JSON), personHandler::updatePerson)
                .DELETE("/v2/person/{uuid}",accept(MediaType.APPLICATION_JSON), personHandler::deletePerson)
                .build();
    }
}
