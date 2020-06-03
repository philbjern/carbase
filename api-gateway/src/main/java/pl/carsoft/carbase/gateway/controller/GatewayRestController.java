package pl.carsoft.carbase.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import pl.carsoft.carbase.gateway.client.CarClient;
import pl.carsoft.carbase.gateway.client.PersonClient;
import pl.carsoft.carbase.gateway.entity.Car;
import pl.carsoft.carbase.gateway.entity.Person;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "api")
@CrossOrigin(origins = "")
public class GatewayRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayRestController.class);

    @Autowired
    PersonClient personClient;

    @Autowired
    CarClient carClient;

    @GetMapping(path = "test")
    public String test() {
        return "Hello World";
    }

    @RequestMapping(path = "time", method = RequestMethod.GET)
    public ResponseEntity<?> getTime() {
        return new ResponseEntity<>(LocalDateTime.now(), HttpStatus.OK);
    }

    @RequestMapping(path = "persons", method = RequestMethod.GET)
    public ResponseEntity<?> findAllPersons() {
        try {
            List<Person> personList = personClient.findAll();
            for (Person person : personList) {
                person.setCarsList(carClient.findPersonCars(person.getId()));
            }
            return new ResponseEntity<>(personList, HttpStatus.OK);
        } catch (RestClientException e) {
            LOGGER.info("Cannot reach service, " + e.getMessage());
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(path = "persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findPersonById(@PathVariable("id") Long personId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "cars", method = RequestMethod.GET)
    public ResponseEntity<?> findAllCars() {
        try {
            List<Car> carList = carClient.findAll();
            return new ResponseEntity<>(carList, HttpStatus.OK);
        } catch (RestClientException e) {
            LOGGER.info("Cannot reach service, " + e.getMessage());
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(path = "cars/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findCarById(@PathVariable("id") Long carId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
