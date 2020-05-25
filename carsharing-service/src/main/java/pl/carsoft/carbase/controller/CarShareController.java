package pl.carsoft.carbase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.carsoft.carbase.repository.CarShareRepository;
import pl.carsoft.carbase.service.CarService;
import pl.carsoft.carbase.service.PersonService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "api")
public class CarShareController {

    @Autowired
    CarService carService;

    @Autowired
    PersonService personService;

    @Autowired
    CarShareRepository carShareRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(path = "persons/{personId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPersonCars(@PathVariable("personId") Long personId) {
        List<Long> carIds = new ArrayList<>();
        carShareRepository.findAll().forEach((el) -> {
            if (el.getPersonId().equals(personId)) {
                carIds.add(el.getCarId());
            }
        });
        if (carIds.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        StringBuilder responseJsonString = new StringBuilder("[");
        carIds.forEach((carId) -> {
            responseJsonString.append(carService.getCarById(carId).getBody());
            responseJsonString.append(",");
        });
        responseJsonString.deleteCharAt(responseJsonString.lastIndexOf(","));
        responseJsonString.append("]");

        try {
            return new ResponseEntity<>(mapper.readTree(responseJsonString.toString()), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "cars/{carId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCarOwners(@PathVariable("carId") Long carId) {
        List<Long> ownerIds = new ArrayList<>();
        carShareRepository.findAll().forEach(el -> {
            if (el.getCarId().equals(carId)) {
                ownerIds.add(el.getPersonId());
            }
        });
        if (ownerIds.isEmpty()) {
            return new ResponseEntity<>(ownerIds, HttpStatus.NOT_FOUND);
        }

        StringBuilder responseJsonString = new StringBuilder("[");
        ownerIds.forEach(id -> {
            responseJsonString.append(personService.getPersonById(id).getBody())
                    .append(",");
        });
        responseJsonString.deleteCharAt(responseJsonString.lastIndexOf(","));
        responseJsonString.append("]");

        try {
            return new ResponseEntity<>(mapper.readTree(responseJsonString.toString()), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @RequestMapping(path = "cars/{personId}", method = RequestMethod.POST)
//    public ResponseEntity<?> addNewCarToPerson(@PathVariable("personId") Long personId, @RequestBody Car car) {
//        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
//    }

    @RequestMapping(path = "persons/{personId}/cars/{carId}", method = RequestMethod.POST)
    public ResponseEntity<?> addCarToPersonById(@PathVariable("personId") Long personId,
                                                @PathVariable("carId") Long carId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "persons/{personId}/cars/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeCarFromPersonById(@PathVariable("personId") Long personId,
                                                     @PathVariable("carId") Long carId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(path = "cars/{carId}/persons/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePersonFromCarById(@PathVariable("personId") Long personId,
                                                     @PathVariable("carId") Long carId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
