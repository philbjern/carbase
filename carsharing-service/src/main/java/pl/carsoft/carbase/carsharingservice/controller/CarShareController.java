package pl.carsoft.carbase.carsharingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.carsoft.carbase.carsharingservice.entity.SharedCar;
import pl.carsoft.carbase.carsharingservice.repository.CarShareRepository;
import pl.carsoft.carbase.carsharingservice.service.CarService;
import pl.carsoft.carbase.carsharingservice.service.PersonService;

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
        if (personService.getPersonById(personId) == null)
            return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);

        List<Long> carIds = new ArrayList<>();
        carShareRepository.findAll().forEach((el) -> {
            if (el.getPersonId().equals(personId)) {
                carIds.add(el.getCarId());
            }
        });
        if (carIds.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        StringBuilder responseJsonString = new StringBuilder("[");
        carIds.forEach((carId) -> {
            responseJsonString.append(carService.getCarById(carId));
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
        if (carService.getCarById(carId) == null) {
            return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
        }

        List<Long> ownerIds = new ArrayList<>();
        carShareRepository.findAll().forEach(el -> {
            if (el.getCarId().equals(carId)) {
                ownerIds.add(el.getPersonId());
            }
        });
        if (ownerIds.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        StringBuilder responseJsonString = new StringBuilder("[");
        ownerIds.forEach(id -> {
            responseJsonString.append(personService.getPersonById(id))
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
        SharedCar carShare = new SharedCar(carId, personId);
        carShareRepository.save(carShare);
        return new ResponseEntity<>(personService.getPersonById(personId), HttpStatus.CREATED);
    }

    @RequestMapping(path = "persons/{personId}/cars/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeCarFromPersonById(@PathVariable("personId") Long personId,
                                                     @PathVariable("carId") Long carId) {
        List<SharedCar> personCars = new ArrayList<>();
        carShareRepository.findAll().forEach(carShare -> {
            if (carShare.getPersonId().equals(personId)) {
                personCars.add(carShare);
            }
        });
        if (!personCars.isEmpty()) {
            for (SharedCar carShare : personCars) {
                if (carShare.getCarId().equals(carId)) {
                    carShareRepository.delete(carShare);
                    return new ResponseEntity<>(personService.getPersonById(personId), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("Person does not own car with this id", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "cars/{carId}/persons/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removePersonFromCarById(@PathVariable("personId") Long personId,
                                                     @PathVariable("carId") Long carId) {
        List<SharedCar> carOwners = new ArrayList<>();
        carShareRepository.findAll().forEach(carShare -> {
            if (carShare.getCarId().equals(carId)) {
                carOwners.add(carShare);
            }
        });
        if (!carOwners.isEmpty()) {
            for (SharedCar carShare : carOwners) {
                if (carShare.getPersonId().equals(personId)) {
                    carShareRepository.delete(carShare);
                    return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
