package com.sorokaandriy.rest_api_mongodb.controller;

import com.sorokaandriy.rest_api_mongodb.collection.Person;
import com.sorokaandriy.rest_api_mongodb.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "Person API", description = "API for managing person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @Operation(summary = "Get all persons")
    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok(service.getAllPersons());
    }

    @Operation(summary = "Save person into db", description = "Save one person into db")
    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person){
        return ResponseEntity.ok(service.save(person));
    }

    @Operation(summary = "Get persons by firstName")
    @GetMapping
    public ResponseEntity<List<Person>> getPersonsByFistName(@RequestParam("firstName") String firstName){
        return ResponseEntity.ok(service.getPersonsByFistName(firstName));
    }

    @Operation(summary = "Delete persons by lastName")
    @DeleteMapping
    public ResponseEntity<Void> deletePersonByLastName(@RequestParam("lastName") String lastName){
        service.deleteByLastName(lastName);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get persons by age")
    @GetMapping("/getBetweenAge")
    public ResponseEntity<List<Person>> getByAgeBetween(@RequestParam("minAge") Integer minAge,
                                                        @RequestParam("maxAge") Integer maxAge){
        return ResponseEntity.ok(service.getByAgeBetween(minAge,maxAge));
    }
}
