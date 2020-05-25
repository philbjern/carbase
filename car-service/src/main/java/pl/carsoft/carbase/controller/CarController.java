package pl.carsoft.carbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.carsoft.carbase.entity.Car;
import pl.carsoft.carbase.repository.CarRepository;

@RestController
@RequestMapping(path = "/api/cars")
public class CarController {

    @Autowired
    CarRepository carRepository;

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findById(@PathVariable("id") Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Car newCar) {
        if (newCar != null) {
            carRepository.save(newCar);
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Car updatedCar) {
        if (updatedCar != null) {
            carRepository.save(updatedCar);
            return new ResponseEntity<>(carRepository.findById(updatedCar.getId()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable("id") Long carId) {
        Car carToRemove = carRepository.findById(carId).orElse(null);
        if (carToRemove != null) {
            carRepository.delete(carToRemove);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
