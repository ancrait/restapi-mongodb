package com.sorokaandriy.rest_api_mongodb.repository;

import com.sorokaandriy.rest_api_mongodb.collection.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {
    List<Person> getByFirstName(String firstName);

    void deletePersonByLastName(String lastName);

    @Query(value = "{ 'age': {$gt: ?0, $lt: ?1}}",fields = "{address: 0, hobbies: 0}")
    List<Person> findPersonByAgeBetween(Integer minAge, Integer maxAge);
}
