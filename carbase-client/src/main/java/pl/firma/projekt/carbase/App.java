package pl.firma.projekt.carbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.firma.projekt.carbase.entity.Person;
import pl.firma.projekt.carbase.http.HttpConnection;
import pl.firma.projekt.carbase.json.JsonParser;

import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_URL = "http://localhost:8080/api";
    private static final int LINE_LENGTH = 19;

    public static void printLine(int lineLength, String character) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < lineLength; i++)
            output.append(character);
        System.out.println(output.toString());
    }

    public static void printMenu() {
        printLine(LINE_LENGTH, "=");
        System.out.println("Choose menu option");
        System.out.println("1. Get persons list");
        System.out.println("2. Insert URL");
        System.out.println("q. Quit application");
        printLine(LINE_LENGTH, "=");
    }

    public static void main(String[] args) {
        HttpConnection connection = new HttpConnection();

        JsonParser parser = new JsonParser();

        Scanner scanner = new Scanner(System.in);

        outer: while (true) {
            printMenu();
            String userInput = scanner.nextLine();
            switch (userInput.charAt(0)) {
                case '1':
                    System.out.println("Getting persons list...");
                    String response = connection.request(API_URL + "/persons", "GET");
                    try {
                        System.out.println(parser.parsePretty(response));
                    } catch (JsonProcessingException e) {
                        System.out.println(e);
                    }
                    break;
                case '2':
                    System.out.println("Enter url... " + API_URL + "{your input}");
                    String userUrl = scanner.nextLine();
                    response = connection.request(API_URL + userUrl, "GET");
                    try {
                        System.out.println(parser.parsePretty(response));
                    } catch (JsonProcessingException e) {
                        System.out.println(e);
                    }
                    break;
                case 'q':
                    System.out.println("Exiting application...");
                    break outer;
                default:
                    System.out.println("Command not recognised, try again");
                    break;
            }
        }

    }

}
