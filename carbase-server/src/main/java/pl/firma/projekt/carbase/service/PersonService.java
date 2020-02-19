package pl.firma.projekt.carbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.firma.projekt.carbase.entity.Person;
import pl.firma.projekt.carbase.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll()
                .forEach(personList::add);
        return personList;
    }

    public Person findById(Integer personId) {
        return personRepository.findById(personId).orElse(null);
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public void deleteById(Integer personId) {
        personRepository.deleteById(personId);
    }

}
