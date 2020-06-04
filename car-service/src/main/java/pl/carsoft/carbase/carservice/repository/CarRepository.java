package pl.carsoft.carbase.carservice.repository;

import org.springframework.data.repository.CrudRepository;
import pl.carsoft.carbase.carservice.entity.Car;

public interface CarRepository extends CrudRepository<Car, Long> {
}
