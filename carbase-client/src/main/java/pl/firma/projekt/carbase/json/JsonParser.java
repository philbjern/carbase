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

    public String stringify(Object obj) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(obj);
        return jsonString;
    }

    public String stringifyPretty(String response) throws JsonProcessingException {
        Object json = objectMapper.readValue(response, Object.class);
        String jsonPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        return jsonPretty;
    }

    public String stringifyPretty(Object obj) throws JsonProcessingException {
        String jsonPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        return jsonPretty;
    }

    public List<Person> getPersonList(String jsonString) throws JsonProcessingException {
        List<Person> result = objectMapper.readValue(jsonString, new TypeReference<List<Person>>() {});
        return result;
    }

    public List<Car> getCarList(String jsonString) throws JsonProcessingException {
        List<Car> result = objectMapper.readValue(jsonString, new TypeReference<List<Car>>() {});
        return result;
    }
}
