package com.example.demo.person;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) // DODAJ TĘ LINIĘ
public class PersonService {
    private List<Person> people;

    public PersonService() {
        // Inicjalizacja w konstruktorze dla bezpieczeństwa
        this.people = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        // Dodanie przykładowych danych po utworzeniu serwisu
        people.add(new Person("Jan", "Kowalski"));
        people.add(new Person("Anna", "Nowak"));
        System.out.println("PersonService initialized with " + people.size() + " persons");
    }

    // Pobiera wszystkie osoby
    public List<Person> getPeople() {
        return new ArrayList<>(people); // Zwracamy kopię dla bezpieczeństwa
    }

    // Pobiera osobę po indeksie
    public Optional<Person> getPerson(int index) {
        if (index >= 0 && index < people.size()) {
            return Optional.of(people.get(index));
        }
        return Optional.empty();
    }

    // Dodaje nową osobę
    public void addPerson(Person person) {
        if (person != null) {
            people.add(person);
        }
    }

    // Aktualizuje osobę na danym indeksie
    public boolean setPerson(int index, Person person) {
        if (index >= 0 && index < people.size() && person != null) {
            people.set(index, person);
            return true;
        }
        return false;
    }

    // Usuwa osobę po indeksie
    public boolean removePerson(int index) {
        if (index >= 0 && index < people.size()) {
            people.remove(index);
            return true;
        }
        return false;
    }
}