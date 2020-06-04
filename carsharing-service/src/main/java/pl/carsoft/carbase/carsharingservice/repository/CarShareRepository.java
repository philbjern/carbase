package pl.carsoft.carbase.carsharingservice.repository;

import org.springframework.data.repository.CrudRepository;
import pl.carsoft.carbase.carsharingservice.entity.SharedCar;

public interface CarShareRepository extends CrudRepository<SharedCar, Long> {
}
