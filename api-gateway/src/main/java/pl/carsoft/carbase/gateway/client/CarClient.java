package pl.carsoft.carbase.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.carsoft.carbase.gateway.entity.Car;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CarClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${service.carservice.url}")
    private String carServiceURL;

    @Value("${service.carshareservice.url}")
    private String carShareServiceURL;

    /**
     * Calls CarService for list of all {@link Car}
     * @return list of all cars
     * @throws RestClientException when service is unreachable
     */
    public List<Car> findAll() throws RestClientException {
        ResponseEntity<Car[]> response = restTemplate.getForEntity(carServiceURL + "/api/cars", Car[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        }
        return Collections.emptyList();
    }

    /**
     * Calls CarShareService for list of {@link Car}s used by a person with {@param personId}
     * @param personId id of a person whose cars are search for
     * @return list of cars used by a person
     * @throws RestClientException when service is unreachable
     */
    public List<Car> findPersonCars(Long personId) throws RestClientException {
        ResponseEntity<Car[]> response = restTemplate.getForEntity(carShareServiceURL + "/api/persons/" + personId,
                Car[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        }
        return Collections.emptyList();
    }

    public Car findCarById(Long carId) {
        ResponseEntity<Car> response = restTemplate.getForEntity(carServiceURL + "/api/cars/" + carId, Car.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        return null;
    }

    public Car save(Car car) {
        ResponseEntity<Car> response = restTemplate.postForEntity(carServiceURL + "/api/cars", car, Car.class);
        if (response.getStatusCode().equals(HttpStatus.CREATED)) {
            return response.getBody();
        }
        return null;
    }

    public void deleteById(Long carId) {
        restTemplate.delete(carServiceURL + "/api/cars/" + carId);
        // delete car share entries
        restTemplate.delete(carShareServiceURL + "/api/cars/" + carId);
    }
}
