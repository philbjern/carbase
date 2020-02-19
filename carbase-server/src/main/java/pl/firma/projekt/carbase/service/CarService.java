package pl.firma.projekt.carbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public List<Car> findAll() {
        List<Car> carList = new ArrayList<>();
        carRepository.findAll()
                .forEach(carList::add);
        return carList;
    }

    public Car findById(Integer carId) {
        return carRepository.findById(carId).orElse(null);
    }

    public void save(Car car) {
        carRepository.save(car);
    }

    public void delete(Car car) {
        carRepository.delete(car);
    }

    public void deleteById(Integer carId) {
        carRepository.deleteById(carId);
    }

}
