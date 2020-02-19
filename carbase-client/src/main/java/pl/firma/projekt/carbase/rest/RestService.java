package pl.firma.projekt.carbase.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.firma.projekt.carbase.entity.Person;

import java.time.Duration;
import java.util.Collections;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }

    public String getAllPersons() {
        try {
            String url = apiUrl + "/persons";
            return this.restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return "Could not connect to server, " + e.getMessage();
        }
    }

    public String getPerson(Integer id) {
        try {
            String url = apiUrl + "/persons/" + id.toString();
            return this.restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return "Error getting person id " + id.toString() + ", " + e.getMessage();
        }
    }

    public String getAllCars() {
        try {
            String url = apiUrl + "/cars";
            return this.restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return "Error getting cars, " + e.getMessage();
        }
    }

    public String getCar(Integer id) {
        try {
            String url = apiUrl + "/cars/" + id.toString();
            return this.restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            return "Error getting car id " + id.toString() + ", " + e.getMessage();
        }
    }

    public String addPerson(Person person) {
        String url = apiUrl + "/persons";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set 'content-typ' header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set 'accept' header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<Person> entity = new HttpEntity<>(person, headers);

        // send POST request
//        ResponseEntity<Person> response = this.restTemplate.postForEntity(url, entity, Person.class);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
