package com.sorokaandriy.rest_api_mongodb.service;

import com.sorokaandriy.rest_api_mongodb.collection.Person;
import com.sorokaandriy.rest_api_mongodb.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        Query query = new Query().with(pageable); //
        List<Criteria> criteria = new ArrayList<>();// список критерій для пошуку

        // якщо імя не null та не пусте,
        // то шукає в документі поле firstName зі значенням переданого зі json(ignore case),
        // якщо находить то добавляє в список зі критеріями
        if(firstname != null && !firstname.isEmpty()){
            criteria.add(Criteria.where("firstName").regex(firstname,"i"));
        }
        if (minAge != null && maxAge != null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if (city != null && !city.isEmpty() && !city.isBlank()){
            criteria.add(Criteria.where("address.city").is(city));
        }
        // об'єднує всі критерії пошуку в спільний масив для query
        if (!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return PageableExecutionUtils.getPage(
                // виконує запит до mongoDb
                mongoTemplate.find(query, Person.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Person.class)
        );
    }
}
