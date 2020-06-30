package pl.firma.projekt.carbase.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;

import java.util.List;

@Component
public class JsonParser {

    private ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public String stringify(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    public String stringifyPretty(String response) {
        try {
            Object json = objectMapper.readValue(response, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            return response;
        }
    }

    public String stringifyPretty(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    public void printPretty(String str) {
        System.out.println(this.stringifyPretty(str));
    }

    public List<Person> getPersonList(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Person>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public Person getPerson(String json) {
        try {
            return objectMapper.readValue(json, Person.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void printCars(List<Car> carList) {
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(carList));
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

    public List<Car> getCarList(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<Car>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public Car getCar(String json) {
        try {
            return objectMapper.readValue(json, Car.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
