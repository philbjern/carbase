package pl.firma.projekt.carbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {

    private static final String BASE_URL = "http://localhost:8080/api";

    public static String request(String urlString, String method) {
        if (urlString.equals("") || method.equals(""))
            return "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();

        } catch (IOException e) {
            System.out.println("Cannot connect to " + urlString + ", " + e.getMessage());
        }
        return "";
    }

    public static void main(String[] args) {
        String response = request(BASE_URL + "/persons", "GET");

        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(response, Object.class);
            String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println(jsonPretty);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

}
