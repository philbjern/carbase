package pl.firma.projekt.carbase.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.firma.projekt.carbase.entity.Car;
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

    @GetMapping("/persons/{personId}")
    public Person getPerson(@PathVariable int personId) {
        Person person = personService.findById(personId);
        if (person == null) {
            throw new RuntimeException("Person id not found " + personId);
        }
        return person;
    }

    @GetMapping("/persons/{personId}/cars")
    public List<Car> getPersonCars(@PathVariable int personId) {
        Person person = personService.findById(personId);
        if (person == null) {
            throw new RuntimeException("Person id not found " + personId);
        }
        List<Car> cars = person.getCars();
        return cars;
    }

    @PostMapping("/persons")
    public Person addPerson(@RequestBody Person person) {
        // also just in case they pass an id in JSON... set id to 0
        // to force a save of new item... instead of update
        person.setId(0);
        personService.save(person);
        return person;
    }

    @PutMapping("/persons")
    public Person updatePerson(@RequestBody Person person) {
        personService.save(person);
        return person;
    }

    @DeleteMapping("/person/{personId}")
    public String deletePerson(@PathVariable int personId) {
        Person person = personService.findById(personId);
        if (person == null) {
            throw new RuntimeException("Person id not found " + personId);
        }
        personService.deleteById(personId);
        return "Deleted person id " + personId;
    }

}
