package pl.firma.projekt.carbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.firma.projekt.carbase.dao.PersonDAO;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Transactional
    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Transactional
    @Override
    public Person findById(int id) {
        return personDAO.findById(id);
    }

    @Transactional
    @Override
    public void save(Person person) {
        personDAO.save(person);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        personDAO.deleteById(id);
    }
}
