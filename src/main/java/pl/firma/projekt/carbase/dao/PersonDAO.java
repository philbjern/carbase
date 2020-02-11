package pl.firma.projekt.carbase.dao;

import pl.firma.projekt.carbase.entity.Person;

import java.util.List;

public interface PersonDAO {
    public List<Person> findAll();

    public Person findById(int id);

    public void save(Person person);

    public void deleteById(int id);
}
