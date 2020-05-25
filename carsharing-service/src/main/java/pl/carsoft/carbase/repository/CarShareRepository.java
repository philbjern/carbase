package pl.carsoft.carbase.repository;

import org.springframework.data.repository.CrudRepository;
import pl.carsoft.carbase.entity.SharedCar;

public interface CarShareRepository extends CrudRepository<SharedCar, Long> {
}
