package pl.carsoft.carbase.repository;

import org.springframework.data.repository.CrudRepository;
import pl.carsoft.carbase.entity.Car;

public interface CarRepository extends CrudRepository<Car, Long> {
}
