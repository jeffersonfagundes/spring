package com.example.crud.restmongo.repository;

import com.example.crud.restmongo.dto.GenderDataDTO;
import com.example.crud.restmongo.entity.Person;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Aggregation(pipeline = {
            "{$group:{_id:\"$gender\",total:{$sum:1},medianAge:{$avg:{$dateDiff:{startDate:\"$dateOfBirth\",endDate:ISODate(),unit:\"year\"}}}}}",
            "{$project:{\"_id\": 0,genderType: {$toString: \"$_id\"},total: \"$total\", medianAge: {$toDouble:\"$medianAge\"}}}"
            })
    List<GenderDataDTO> calculateTheDataOfGenresIn();
}
