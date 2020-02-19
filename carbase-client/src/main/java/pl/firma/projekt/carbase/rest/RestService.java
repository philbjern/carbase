package pl.firma.projekt.carbase.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;

import java.time.Duration;
import java.util.Collections;

@Service
public class RestService {

    private final RestTemplate restTemplate;
    private HttpHeaders headers;

    @Value("${api.url}")
    private String apiUrl;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        setupHeaders();
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }

    public void setupHeaders() {
        // create headers
        headers = new HttpHeaders();
        // set 'content-typ' header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set 'accept' header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
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

    public String customGetRequest(String url) {
        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            return "Error requesting " + url + ", " + e.getMessage();
        }
    }

    public String addPerson(Person person) {
        String url = apiUrl + "/persons";
        // build the request
        HttpEntity<Person> entity = new HttpEntity<>(person, this.headers);
        // send POST request
        // ResponseEntity<Person> response = this.restTemplate.postForEntity(url, entity, Person.class);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public String addCar(Car car) {
        String url = apiUrl + "/cars";
        // build the request
        HttpEntity<Car> entity = new HttpEntity<>(car, this.headers);
        // send POST request
        // ResponseEntity<Person> response = this.restTemplate.postForEntity(url, entity, Person.class);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public String updatePerson(Person person) {
        String url = apiUrl + "/persons";
        HttpEntity<Person> entity = new HttpEntity<>(person, this.headers);
        // send PUT request to update person
        ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public String deletePerson(Integer personId) {
        String url = apiUrl + "/persons/" + personId.toString();
        // send DELETE request to delete person with given id
        try {
            this.restTemplate.delete(url);
            return "Deleted person id " + personId.toString();
        } catch (RestClientException e) {
            return "Error deleting person, " + e.getMessage();
        }
    }

    public String deleteCar(Integer carId) {
        String url = apiUrl + "/cars/" + carId.toString();
        // send DELETE request to delete person with given id
        try {
            this.restTemplate.delete(url);
            return "Deleted car id " + carId.toString();
        } catch (RestClientException e) {
            return "Error deleting car, " + e.getMessage();
        }
    }

}
