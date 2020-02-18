package pl.firma.projekt.carbase.http;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class HttpConnection {

    public String request(String urlString, String method, String json) {
        if (urlString.equals("") || method.equals("")) {
            throw new IllegalArgumentException("URL and request method are required");
        }
        if (json == null && json.isEmpty() && (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT"))) {
            throw new IllegalArgumentException("Request body is required when using POST or PUT method");
        }

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (json != null && !json.isEmpty()) {
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = json.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            // Read the response from input stream
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                connection.disconnect();
                return response.toString();
            }

        } catch (IOException e) {
            return "Error connecting to " + urlString + ": " + e.getMessage();
        }
    }

    public String request(String url, String method) {
        return this.request(url, method, "");
    }

}
