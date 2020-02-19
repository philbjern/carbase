package pl.firma.projekt.carbase.repository;

import org.springframework.data.repository.CrudRepository;
import pl.firma.projekt.carbase.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
