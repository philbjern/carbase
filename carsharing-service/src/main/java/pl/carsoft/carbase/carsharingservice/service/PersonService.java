package pl.carsoft.carbase.carsharingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import java.util.List;

@Service
public class PersonService {

    @Value("${service.personservice.url}")
    private String personServiceBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Entity> getCarUsers(Long carId) {
        return restTemplate.getForObject(personServiceBaseUrl + "/api/persons", List.class);
    }

    public String getPersonById(Long personId) {
        ResponseEntity<String> response = restTemplate.getForEntity(personServiceBaseUrl + "/api/persons/" + personId
                , String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        return null;
    }
}
