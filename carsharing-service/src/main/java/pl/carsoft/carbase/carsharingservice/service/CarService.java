package pl.carsoft.carbase.carsharingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CarService {

    @Value("${service.personservice.url}")
    private String personServiceUrl;

    @Value("${service.carservice.url}")
    private String carServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getCarById(Long carId) {
        ResponseEntity<String> response = restTemplate.getForEntity(carServiceUrl + "/api/cars/" + carId, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        return null;
    }

}
