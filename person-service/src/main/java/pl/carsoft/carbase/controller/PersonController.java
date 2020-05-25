package pl.carsoft.carbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.carsoft.carbase.entity.Person;
import pl.carsoft.carbase.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/person/")

public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);

        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Person person) {
        if (person != null) {
            personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Person person) {
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        personRepository.save(person);
        Person updatedPerson = personRepository.findById(person.getId()).orElse(null);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Person person) {
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        personRepository.delete(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        personRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

