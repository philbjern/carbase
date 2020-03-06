package pl.firma.projekt.carbase.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;
import pl.firma.projekt.carbase.service.CarService;
import pl.firma.projekt.carbase.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class CarRestController {

    private CarService carService;
    private PersonService personService;

    @Autowired
    public CarRestController(CarService carService, PersonService personService) {
        this.carService = carService;
        this.personService = personService;
    }

    @GetMapping("/cars")
    public List<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<?> findCarById(@PathVariable int carId) {
        Car car = carService.findById(carId);
        if (car == null) {
            return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/cars/{carId}/persons")
    public ResponseEntity<?> findCarOwners(@PathVariable int carId) {
        Car car = carService.findById(carId);
        if (car == null) {
            return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
        }
        List<Person> owners = personService.findAll()
                .stream()
                .filter((Person p) -> p.getCars().contains(car))
                .collect(Collectors.toList());

        if (owners.isEmpty()) {
            return new ResponseEntity<>("Owners not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        car.setId(0);
        carService.save(car);
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    @PutMapping("/cars")
    public ResponseEntity<?> updateCar(@RequestBody Car car) {
        carService.save(car);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<?> deleteCar(@PathVariable int carId) {
        Car car = carService.findById(carId);
        if (car == null) {
            return new ResponseEntity<>("Car id not found", HttpStatus.NOT_FOUND);
        }
        carService.deleteById(carId);
        return new ResponseEntity<>("Deleted car id " + carId, HttpStatus.OK);
    }
}
