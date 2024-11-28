package com.example.soap.person.repository;

import com.example.soap.person.entity.Person;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Aggregation(pipeline = {
            "{$match: ?0}"
    })
    public List<Person> findBy(@Param("matchCriteria") Map<String, Pattern> filters);
}
