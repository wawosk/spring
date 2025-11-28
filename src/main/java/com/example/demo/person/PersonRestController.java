package com.example.demo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PersonRestController {

    private final PersonService personService;

    @Autowired
    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    // GET - pobiera wszystkie osoby
    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        List<Person> people = personService.getPeople();
        if (people.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(people);
    }

    // GET - pobiera osobę po indeksie
    @GetMapping("/{index}")
    public ResponseEntity<Person> getPerson(@PathVariable int index) {
        Optional<Person> person = personService.getPerson(index);
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST - dodaje nową osobę
    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        if (person == null || person.getFirstName() == null || person.getFamilyName() == null) {
            return ResponseEntity.badRequest().build();
        }
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    // PUT - aktualizuje istniejącą osobę
    @PutMapping("/{index}")
    public ResponseEntity<Person> updatePerson(@PathVariable int index, @RequestBody Person person) {
        if (person == null || person.getFirstName() == null || person.getFamilyName() == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean updated = personService.setPerson(index, person);
        if (updated) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - usuwa osobę
    @DeleteMapping("/{index}")
    public ResponseEntity<Void> removePerson(@PathVariable int index) {
        boolean removed = personService.removePerson(index);
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}