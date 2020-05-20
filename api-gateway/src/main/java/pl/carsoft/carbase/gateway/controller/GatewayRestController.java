package pl.carsoft.carbase.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.carsoft.carbase.gateway.client.PersonClient;
import pl.carsoft.carbase.gateway.entity.Person;

import java.util.List;

@RestController
@RequestMapping(path = "api")
@CrossOrigin(origins = "")
public class GatewayRestController {

    @Autowired
    PersonClient personClient;

    @GetMapping(path = "/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping(path = "persons")
    public ResponseEntity<?> findAllPersons() {
        List<Person> personList = personClient.findAll();
        if (personList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }
}
