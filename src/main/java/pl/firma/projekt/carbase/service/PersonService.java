package pl.firma.projekt.carbase.service;

import pl.firma.projekt.carbase.entity.Person;

import java.util.List;

public interface PersonService {
    public List<Person> findAll();

    public Person findById(int id);

    public void save(Person person);

    public void deleteById(int id);
}
