package com.sorokaandriy.rest_api_mongodb.controller;

import com.sorokaandriy.rest_api_mongodb.collection.Person;
import com.sorokaandriy.rest_api_mongodb.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/search")
    public ResponseEntity<Page<Person>> search(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(service.search(firstname,minAge,maxAge,city,pageable));
    }
}
