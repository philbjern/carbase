package pl.carsoft.carbase.personservice.repository;

import org.springframework.data.repository.CrudRepository;
import pl.carsoft.carbase.personservice.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
