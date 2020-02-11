package pl.firma.projekt.carbase.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarRestController {

    private CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<Car> findAll() {
        return carService.findAll();
    }

}
