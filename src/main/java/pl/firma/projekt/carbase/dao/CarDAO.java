package pl.firma.projekt.carbase.dao;

import pl.firma.projekt.carbase.entity.Car;

import java.util.List;

public interface CarDAO {
    public List<Car> findAll();

    public Car findById(int id);

    public void save(Car car);

    public void deleteById(int id);
}
