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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "api")
@CrossOrigin(origins = "http://localhost:8080")
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
            List<Person> result = new ArrayList<>();
            personClient.findAll().forEach(person -> {
                person.setCarsList(carClient.findPersonCars(person.getId()));
                result.add(person);
            });
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RestClientException e) {
            LOGGER.info("Cannot reach service, " + e.getMessage());
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(path = "persons/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findPersonById(@PathVariable("id") Long personId) {
        try {
            Person person = personClient.findPersonById(personId);
            if (person != null) {
                person.setCarsList(carClient.findPersonCars(personId));
                return new ResponseEntity<>(person, HttpStatus.OK);
            }
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
        } catch (RestClientException e) {
          LOGGER.info("Cannot reach service, " + e.getMessage());
          return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(path = "cars", method = RequestMethod.GET)
    public ResponseEntity<?> findAllCars() {
        try {
            List<Car> result = new ArrayList<>();
            carClient.findAll().forEach(car -> {
                car.setOwnerList(personClient.findCarOwners(car.getId()));
                result.add(car);
            });
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RestClientException e) {
            LOGGER.info("Cannot reach service, " + e.getMessage());
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(path = "cars/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findCarById(@PathVariable("id") Long carId) {
        try {
            Car car = carClient.findCarById(carId);
            if (car != null) {
                car.setOwnerList(personClient.findCarOwners(car.getId()));
                return new ResponseEntity<>(car, HttpStatus.OK);
            }
            return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
        } catch (RestClientException e) {
            LOGGER.info("Cannot reach service, " + e.getMessage());
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
