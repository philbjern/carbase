package pl.carsoft.carbase.service;

import org.springframework.beans.factory.annotation.Value;
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
        return restTemplate.getForObject(personServiceBaseUrl + "/api/person", List.class);
    }

    public ResponseEntity<String> getPersonById(Long personId) {
        return restTemplate.getForEntity(personServiceBaseUrl + "/api/person/" + personId, String.class);
    }
}
