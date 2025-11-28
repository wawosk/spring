package com.example.demo.person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Wyświetla listę wszystkich osób
    @GetMapping
    public String getPeopleList(Model model) {
        model.addAttribute("people", personService.getPeople());
        return "people-list";
    }

    // Wyświetla szczegóły pojedynczej osoby
    @GetMapping("/{index}")
    public String getPersonDetails(@PathVariable int index, Model model) {
        Optional<Person> person = personService.getPerson(index);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("index", index);
            return "person-details";
        } else {
            return "redirect:/people";
        }
    }

    // Wyświetla formularz dodawania nowej osoby
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new Person());
        return "person-form";
    }

    // Przetwarza dodawanie nowej osoby
    @PostMapping("/add")
    public String addPerson(@ModelAttribute Person person) {
        personService.addPerson(person);
        return "redirect:/people";
    }

    // Wyświetla formularz edycji osoby
    @GetMapping("/edit/{index}")
    public String showEditForm(@PathVariable int index, Model model) {
        Optional<Person> person = personService.getPerson(index);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("index", index);
            return "person-form";
        } else {
            return "redirect:/people";
        }
    }

    // Przetwarza edycję osoby
    @PostMapping("/edit/{index}")
    public String updatePerson(@PathVariable int index, @ModelAttribute Person person) {
        personService.setPerson(index, person);
        return "redirect:/people";
    }

    // Usuwa osobę
    @GetMapping("/delete/{index}")
    public String deletePerson(@PathVariable int index) {
        personService.removePerson(index);
        return "redirect:/people";
    }
}