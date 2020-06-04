package pl.carsoft.carbase.gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.carsoft.carbase.gateway.entity.Car;
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

    /**
     * Calls PersonService for list of all {@link Person} in carbase database.
     *
     * @return list of all persons
     * @throws RestClientException when service is unreachable
     */
    public List<Person> findAll() throws RestClientException {
        ResponseEntity<Person[]> response = restTemplate.getForEntity(personServiceURL + "/api/persons",
                Person[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        }
        return Collections.emptyList();
    }

    /**
     * Calls CarShareService for list of {@link Person} users of {@link Car} with id of {@param carId}
     *
     * @param carId id of car which users are requested
     * @return list of car users
     * @throws RestClientException when service is unreachable
     */
    // Remove, duplicated in CarClient
    public List<Car> findPersonCars(Long carId) throws RestClientException {
        ResponseEntity<Car[]> response = restTemplate.getForEntity(carShareServiceURL + "/api/cars/" + carId,
                Car[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        }
        return Collections.emptyList();
    }

    public Person findPersonById(Long personId) {
        ResponseEntity<Person> response = restTemplate.getForEntity(personServiceURL + "/api/persons/" + personId,
                Person.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        return null;
    }

    public List<Person> findCarOwners(Long id) {
        return Collections.emptyList();
    }
}
