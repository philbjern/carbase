package pl.carsoft.carbase.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import pl.carsoft.carbase.gateway.client.CarClient;
import pl.carsoft.carbase.gateway.client.CarShare;
import pl.carsoft.carbase.gateway.client.PersonClient;
import pl.carsoft.carbase.gateway.entity.Car;
import pl.carsoft.carbase.gateway.entity.Person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "api")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class GatewayRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayRestController.class);

    @Autowired
    PersonClient personClient;

    @Autowired
    CarClient carClient;

    @Autowired
    CarShare carShareClient;

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
            e.printStackTrace();
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

    @RequestMapping(path = "persons", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            // Save person
            Person result = personClient.save(person);
            if (result != null) {
                // Save person cars
                // create non existing cars
                if (person.getCarsList() != null) {
                    person.getCarsList().forEach(car -> {
                        Long carId;
                        if (car.getId() == null) {
                            // create new car
                            Car newCar = carClient.save(car);
                            System.out.println(newCar);
                            carId = newCar.getId();
                        } else {
                            // car exists
                            carId = car.getId();
                        }
                        carShareClient.save(result.getId(), carId);
                    });
                }
                return new ResponseEntity<>(this.findPersonById(result.getId()).getBody(), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Person not created", HttpStatus.NOT_MODIFIED);
            }
        } catch (RestClientException e) {
            LOGGER.info("Could not create person. " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "cars", method = RequestMethod.POST)
    public ResponseEntity<?> createCar(@RequestBody Car car) {
        if (car == null) {
            return new ResponseEntity<>("Request body must not be empty", HttpStatus.BAD_REQUEST);
        }
        try {
            carClient.save(car);
        } catch (RestClientException e) {
            LOGGER.info("Could not create car. " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Car successfully created", HttpStatus.OK);
    }

    @RequestMapping(path = "persons/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@PathVariable("id") Long personId, @RequestBody Person person) {
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (personClient.findPersonById(personId) == null) {
            return new ResponseEntity<>("Person doesn't exist", HttpStatus.NOT_FOUND);
        }

        try {
            personClient.updatePerson(person);
        } catch (RestClientException e) {
            LOGGER.info("Update person error: Could not update person info. " + e.getMessage());
            return new ResponseEntity<>("Update person error: Could not update person info.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            carShareClient.updatePersonCars(personId, person.getCarsList());
        } catch (RestClientException e) {
            LOGGER.info("Update person error: Could not update person cars information. " + e.getMessage());
            return new ResponseEntity<>("Update person error: Could not update person cars info.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "cars/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCar(@PathVariable("id") Long carId, @RequestBody Car car) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long personId) {
        try {
            personClient.deleteById(personId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RestClientException e) {
            LOGGER.info("Could not delete person. " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "cars/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long carId) {
        try {
            carClient.deleteById(carId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RestClientException e) {
            LOGGER.info("Could not delete car. " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
