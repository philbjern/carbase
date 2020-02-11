package pl.firma.projekt.carbase.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.firma.projekt.carbase.entity.Person;
import pl.firma.projekt.carbase.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonRestController {

    private PersonService personService;

    @Autowired
    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> findAll() {
        return personService.findAll();
    }

}
