package pl.firma.projekt.carbase.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.firma.projekt.carbase.entity.Person;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PersonDAOHibernateImpl implements PersonDAO {

    private EntityManager entityManager;

    @Autowired
    public PersonDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Person> findAll() {
        // get the current hibernate sesion
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query<Person> query = currentSession.createQuery("from Person", Person.class);
        // execute query and get result list
        List<Person> persons = query.getResultList();
        // return the persons
        return persons;
    }

    @Override
    public Person findById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        // get the person
        Person person = currentSession.get(Person.class, id);
        // return the person
        return person;
    }

    @Override
    public void save(Person person) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        // save the person
        currentSession.saveOrUpdate(person);
    }

    @Override
    public void deleteById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        // get Person and delete
        // currentSession.delete(this.findById(id));
        Query query = currentSession.createQuery("delete from Person where id=:personId");
        query.setParameter("personId", id);
        query.executeUpdate();
    }
}
