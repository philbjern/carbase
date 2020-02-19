package pl.firma.projekt.carbase.repository;

import org.springframework.data.repository.CrudRepository;
import pl.firma.projekt.carbase.entity.Car;

public interface CarRepository extends CrudRepository<Car, Integer> {
}
