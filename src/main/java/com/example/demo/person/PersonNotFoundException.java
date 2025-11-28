package com.example.demo.person;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(int index) {
        super("Person with index " + index + " not found");
    }
}