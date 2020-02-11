package pl.firma.projekt.carbase.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.firma.projekt.carbase.entity.Car;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CarDAOHibernateImpl implements CarDAO {

    private EntityManager entityManager;

    @Autowired
    public CarDAOHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Car> findAll() {
        // get the current hibernate sesion
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query<Car> query = currentSession.createQuery("from Car", Car.class);
        // execute query and get result list
        List<Car> cars = query.getResultList();
        // return the persons
        return cars;
    }

    @Override
    public Car findById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        // get the person
        Car car = currentSession.get(Car.class, id);
        // return the person
        return car;
    }

    @Override
    public void save(Car car) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        // save the person
        currentSession.saveOrUpdate(car);
    }

    @Override
    public void deleteById(int id) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);
        // get Person and delete
        // currentSession.delete(this.findById(id));
        Query query = currentSession.createQuery("delete from Car where id=:carId");
        query.setParameter("carId", id);
        query.executeUpdate();
    }
}
