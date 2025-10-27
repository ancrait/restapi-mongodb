package com.sorokaandriy.rest_api_mongodb.service;

import com.sorokaandriy.rest_api_mongodb.collection.Person;
import com.sorokaandriy.rest_api_mongodb.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final MongoTemplate mongoTemplate;


    public PersonService(PersonRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
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


    public Page<Person> search(String firstname, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if(firstname != null && !firstname.isEmpty()){
            criteria.add(Criteria.where("fistName").regex(firstname,"i"));
        }
        if (minAge != null && maxAge !=null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if (city.isEmpty() && city.isBlank()){
            criteria.add(Criteria.where("address").is(city));
        }
        return null;
    }
}
