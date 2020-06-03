package pl.carsoft.carbase.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.carsoft.carbase.gateway.entity.Person;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PersonClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${service.personservice.url}")
    private String personServiceURL;

    @Value("${service.carshareservice.url}")
    private String carShareServiceURL;

    public List<Person> findAll() throws RestClientException {
        // make request for list of all persons
        ResponseEntity<Person[]> response = restTemplate.getForEntity(personServiceURL + "/api/person", Person[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        }
        return Collections.emptyList();
    }
}
