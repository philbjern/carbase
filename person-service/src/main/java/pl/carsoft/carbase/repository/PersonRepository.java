package pl.carsoft.carbase.repository;

import org.springframework.data.repository.CrudRepository;
import pl.carsoft.carbase.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
