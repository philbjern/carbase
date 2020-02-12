package pl.firma.projekt.carbase.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.firma.projekt.carbase.entity.Person;

import java.util.List;

public class JsonParser {

    private ObjectMapper objectMapper;

    public JsonParser() {
        this.objectMapper = new ObjectMapper();
    }

    public String parsePretty(String response) throws JsonProcessingException {
        Object json = objectMapper.readValue(response, Object.class);
        String jsonPretty = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

        return jsonPretty;
    }

    public List<Person> parsePersonList(String jsonString) throws JsonProcessingException {
        List<Person> result = objectMapper.readValue(jsonString, new TypeReference<List<Person>>() {});
        return result;
    }

}
