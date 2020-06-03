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

    public List<Car> findAll() throws RestClientException {
        ResponseEntity<Car[]> response = restTemplate.getForEntity(carServiceURL + "/api/cars", Car[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        }
        return Collections.emptyList();
    }

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
}
