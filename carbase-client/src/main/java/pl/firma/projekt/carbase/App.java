package pl.firma.projekt.carbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;
import pl.firma.projekt.carbase.http.HttpConnection;
import pl.firma.projekt.carbase.json.JsonParser;

import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_URL = "http://localhost:8080/api/";
    private static final int LINE_LENGTH = 20;
    private HttpConnection connection;
    private JsonParser parser;
    private Scanner scanner;

    public App() {
        connection = new HttpConnection();
        parser = new JsonParser();
        scanner = new Scanner(System.in);
    }

    public void printLine(int lineLength, String character) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < lineLength; i++)
            output.append(character);
        System.out.println(output.toString());
    }

    public void printMenu() {
        printLine(LINE_LENGTH, "=");
        System.out.println("Choose menu option");
        System.out.println("1. Get person list");
        System.out.println("2. Custom GET request");
        System.out.println("3. Add person");
        System.out.println("4. Add car");
        System.out.println("5. Add existing car to a person");
        System.out.println("6. Delete person");
        System.out.println("7. Delete car");
        System.out.println("q. Quit application");
        printLine(LINE_LENGTH, "=");
    }

    public void getPersonList() throws JsonProcessingException {
        System.out.println("Getting persons list...");
        String response = connection.request(API_URL + "persons", "GET");
        System.out.println(parser.stringifyPretty(response));
    }

    public void getCarList() throws JsonProcessingException {
        System.out.println("Getting cars list...");
        String response = connection.request(API_URL + "cars", "GET");
        System.out.println(parser.stringifyPretty(response));
    }

    public void userCustomGetRequest() throws JsonProcessingException {
        System.out.println("Enter url (" + API_URL + "{your input})");
        String userUrl = scanner.nextLine();
        String response = connection.request(API_URL + userUrl, "GET");
        System.out.println(parser.stringifyPretty(response));
    }

    public void addPerson() throws JsonProcessingException {
        System.out.println("Insert person's data");
        Person person = new Person();

        System.out.println("First name: ");
        String userInput = scanner.nextLine();
        person.setFirstName(userInput);

        System.out.println("Last name: ");
        userInput = scanner.nextLine();
        person.setLastName(userInput);

        System.out.println("Email: ");
        userInput = scanner.nextLine();
        person.setEmail(userInput);

        String url = API_URL + "persons";
        String response = connection.request(url, "POST", parser.stringify(person));
        System.out.println(parser.stringifyPretty(response));
    }

    public void addCar() throws JsonProcessingException {
        System.out.println("Insert car's details");
        Car car = new Car();

        System.out.println("Make: ");
        String userInput = scanner.nextLine();
        car.setMake(userInput);

        System.out.println("Model: ");
        userInput = scanner.nextLine();
        car.setModel(userInput);

        while (true) {
            System.out.println("Production year: ");
            userInput = scanner.nextLine();
            try {
                car.setProductionYear(Integer.parseInt(userInput));
                break;
            } catch (NumberFormatException e) {
                continue;
            }
        }

        System.out.println("Fuel: (DIESEL, PETROL, PETROL_LPG, ELECTRIC)");
        userInput = scanner.nextLine();
        car.setFuel(userInput);

        System.out.println("Engine volume: ");
        userInput = scanner.nextLine();
        car.setEngineVolume(userInput);

        String url = API_URL + "cars";
        String response = connection.request(url, "POST", parser.stringify(car));
        System.out.println(parser.stringifyPretty(response));
    }

    public void addExistingCarToPerson() throws JsonProcessingException {
        Car car;
        Person person;
        String response, userInput;
        int personId, carId;

        while (true) {
            System.out.println("Choose person id");
            response = connection.request(API_URL + "persons", "GET");
            System.out.println(parser.stringifyPretty(response));

            while (true) {
                userInput = scanner.nextLine();
                try {
                    personId = Integer.parseInt(userInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Person's id must be a number");
                }
            }

            List<Person> personList = parser.getPersonList(response);
            int finalPersonId = personId;
            person = personList.stream().filter((Person p) -> {
                return p.getId() == finalPersonId;
            }).findAny().orElse(null);

            if (person == null) {
                System.out.println("Choose existing person's id");
                continue;
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Choose car id");
            response = connection.request(API_URL + "cars", "GET");
            System.out.println(parser.stringifyPretty(response));
            while (true) {
                userInput = scanner.nextLine();
                try {
                    carId = Integer.parseInt(userInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Car's id must be a number");
                }
            }

            int finalCarId = carId;
            car = parser.getCarList(response).stream().filter((Car c) -> {
                return c.getId() == finalCarId;
            }).findAny().orElse(null);

            if (car == null) {
                System.out.println("Choose existing car's id");
                continue;
            } else {
                break;
            }
        }

        person.addCar(car);
        response = connection.request(API_URL + "persons", "PUT", parser.stringify(person));
        System.out.println(parser.stringifyPretty(response));
    }

    public void deletePerson() throws JsonProcessingException {
        this.getPersonList();
        System.out.println("Select person id to remove");
        String input;
        int personId;

        while (true) {
            input = scanner.nextLine();
            try {
                personId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Must provide number");
            }
        }

        String url = API_URL + "persons/" + String.valueOf(personId);
        String response = connection.request(url, "DELETE");
        System.out.println(response);
    }

    public void deleteCar() throws JsonProcessingException {
        this.getCarList();
        System.out.println("Select car id to remove");
        String input;
        int carId;

        while (true) {
            input = scanner.nextLine();
            try {
                carId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Must provide number");
            }
        }

        String url = API_URL + "cars/" + String.valueOf(carId);
        String response = connection.request(url, "DELETE");
        System.out.println(response);
    }

    public void run() throws JsonProcessingException {
        outer:
        while (true) {
            printMenu();
            String userInput = scanner.nextLine();
            switch (userInput.charAt(0)) {
                case '1':
                    getPersonList();
                    break;
                case '2':
                    userCustomGetRequest();
                    break;
                case '3':
                    addPerson();
                    break;
                case '4':
                    addCar();
                    break;
                case '5':
                    addExistingCarToPerson();
                    break;
                case '6':
                    deletePerson();
                    break;
                case '7':
                    deleteCar();
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

    public static void main(String[] args) throws JsonProcessingException {
        App app = new App();
        app.run();
    }

}
