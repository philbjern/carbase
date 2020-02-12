package pl.firma.projekt.carbase.rest;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getPerson(@PathVariable int personId) {
        Person person = personService.findById(personId);
        if (person == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/persons/{personId}/cars")
    public ResponseEntity<?> getPersonCars(@PathVariable int personId) {
        Person person = personService.findById(personId);
        if (person == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        List<Car> cars = person.getCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @PostMapping("/persons")
    public ResponseEntity<?> addPerson(@RequestBody Person person) {
        // also just in case they pass an id in JSON... set id to 0
        // to force a save of new item... instead of update
        person.setId(0);
        personService.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PostMapping("/persons/{personId}/cars")
    public ResponseEntity<?> addPersonCar(@PathVariable int personId, @RequestBody Car car) {
        Person person = personService.findById(personId);
        if (person == null) {
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        }
        car.setId(0);
        person.addCar(car);
        personService.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("/persons")
    public ResponseEntity<?> updatePerson(@RequestBody Person person) {
        personService.save(person);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/person/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable int personId) {
        Person person = personService.findById(personId);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        personService.deleteById(personId);
        return new ResponseEntity<>("Deleted person id " + personId, HttpStatus.OK);
    }

}
