package com.sorokaandriy.rest_api_mongodb.service;

import com.sorokaandriy.rest_api_mongodb.collection.Person;
import com.sorokaandriy.rest_api_mongodb.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;


    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAllPersons(){
        return repository.findAll();
    }

    public Person save(Person person){
        return repository.save(person);
    }

    public List<Person> getPersonsByFistName(String firstName){
        return repository.getByFirstName(firstName);
    }

    public List<Person> getByAgeBetween(Integer minAge, Integer maxAge){
        return repository.findPersonByAgeBetween(minAge, maxAge);
    }

    public void deleteByLastName(String lastName){
        repository.deletePersonByLastName(lastName);
    }


}
