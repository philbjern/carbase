package pl.firma.projekt.carbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.firma.projekt.carbase.dao.CarDAO;
import pl.firma.projekt.carbase.entity.Car;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CarServiceImpl implements CarService {

    private CarDAO carDAO;

    @Autowired
    public CarServiceImpl(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    @Transactional
    @Override
    public List<Car> findAll() {
        return carDAO.findAll();
    }

    @Transactional
    @Override
    public Car findById(int id) {
        return carDAO.findById(id);
    }

    @Transactional
    @Override
    public void save(Car car) {
        carDAO.save(car);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        carDAO.deleteById(id);
    }
}
