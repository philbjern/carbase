package pl.firma.projekt.carbase.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;

import java.util.List;

public class JsonParser {

    private ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public String stringify(Object obj) {
        try {
            String jsonString = objectMapper.writeValueAsString(obj);
            return jsonString;
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    public String stringifyPretty(String response) {
        try {
            Object json = objectMapper.readValue(response, Object.class);
            String jsonPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            return jsonPretty;
        } catch (JsonProcessingException e) {
            return response;
        }
    }

    public String stringifyPretty(Object obj) {
        try {
            String jsonPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            return jsonPretty;
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    public List<Person> getPersonList(String jsonString) {
        try {
            List<Person> result = objectMapper.readValue(jsonString, new TypeReference<List<Person>>() {});
            return result;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public List<Car> getCarList(String jsonString) {
        try {
            List<Car> result = objectMapper.readValue(jsonString, new TypeReference<List<Car>>() {});
            return result;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
