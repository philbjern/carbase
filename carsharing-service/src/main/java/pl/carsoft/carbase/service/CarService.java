package pl.carsoft.carbase.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import java.util.List;

@Service
public class CarService {

    @Value("${service.personservice.url}")
    private String personServiceUrl;

    @Value("${service.carservice.url}")
    private String carServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Entity> getAllCars() {
//        System.out.println(carServiceUrl);
        return restTemplate.getForObject(carServiceUrl + "/api/cars", List.class);
    }

    public ResponseEntity<String> getCarById(Long carId) {
        return restTemplate.getForEntity(carServiceUrl + "/api/cars/" + carId, String.class);
    }

}
