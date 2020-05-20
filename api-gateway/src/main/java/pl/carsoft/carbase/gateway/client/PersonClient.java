package pl.carsoft.carbase.gateway.client;

import org.springframework.stereotype.Service;
import pl.carsoft.carbase.gateway.entity.Person;

import java.util.Collections;
import java.util.List;

@Service
public class PersonClient {

    public List<Person> findAll() {
        // make request for list of all persons
        return Collections.emptyList();
    }
}
