package pl.carsoft.carbase.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.carsoft.carbase.gateway.entity.Car;
import pl.carsoft.carbase.gateway.entity.Person;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarShare {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${service.carshareservice.url}")
    private String carShareServiceURL;

    public boolean save(Long personId, Long carId) {
        ResponseEntity<Person> response =
                restTemplate.postForEntity(carShareServiceURL + "/api/persons/" + personId + "/cars/" + carId, null,
                        Person.class);
        return response.getStatusCode().equals(HttpStatus.CREATED);
    }

    public void updatePersonCars(Long personId, List<Car> carsList) {
        List<Long> carsIds = carsList.stream().map(Car::getId).collect(Collectors.toList());
        restTemplate.put(carShareServiceURL + "/api/persons/" + personId, carsIds);
    }
}
