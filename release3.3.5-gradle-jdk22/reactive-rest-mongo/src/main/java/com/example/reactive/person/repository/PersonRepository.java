package com.example.reactive.person.repository;

import com.example.reactive.person.entity.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

    Flux<Person> findAllBy(Pageable pageable);
}
