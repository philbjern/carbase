package pl.firma.projekt.carbase.service;

import pl.firma.projekt.carbase.entity.Car;

import java.util.List;

public interface CarService {
    public List<Car> findAll();

    public Car findById(int id);

    public void save(Car car);

    public void deleteById(int id);
}
