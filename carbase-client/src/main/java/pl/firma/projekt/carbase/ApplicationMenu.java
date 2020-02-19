package pl.firma.projekt.carbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.firma.projekt.carbase.entity.Car;
import pl.firma.projekt.carbase.entity.Person;
import pl.firma.projekt.carbase.json.JsonParser;
import pl.firma.projekt.carbase.rest.RestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ApplicationMenu {

    @Autowired
    JsonParser parser;

    @Autowired
    RestService restService;

    private Scanner scanner;

    public ApplicationMenu() {
        this.scanner =  new Scanner(System.in);
    }

    public void printLine(int lineLength, String character) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < lineLength; i++)
            output.append(character);
        System.out.println(output.toString());
    }

    public void printMenu() {
        List<String> menuEntries = new ArrayList<>();
        menuEntries.add("Choose menu option");
        menuEntries.add("1. Get person list");
        menuEntries.add("2. Get car list");
        menuEntries.add("3. Add person");
        menuEntries.add("4. Add car");
        menuEntries.add("5. Add existing car to a person");
        menuEntries.add("6. Remove car from a person");
        menuEntries.add("7. Delete person");
        menuEntries.add("8. Delete car");
        menuEntries.add("9. Custom GET request");
        menuEntries.add("q. Quit application");

        Optional<String> item = menuEntries.stream()
                .reduce((s1, s2) -> s1.length() > s2.length() ? s1 : s2);
        int entryMaxLength = item.map(String::length).orElse(20);

        printLine(entryMaxLength, "=");
        for (String s : menuEntries)
            System.out.println(s);
        printLine(entryMaxLength, "=");
    }

    public void printPersonList() {
        System.out.println("Getting persons list...");
        String response = restService.getAllPersons();
        parser.printPretty(response);
    }

    public void getCarList() {
        System.out.println("Getting cars list...");
        String response = restService.getAllCars();
        parser.printPretty(response);
    }

    public void userCustomGetRequest() {
        System.out.println("Enter url (" + restService.getApiUrl() + "{your input})");
        String userUrl = scanner.nextLine();
        if (userUrl.charAt(0) == 'q')
            return;
        String response = restService.customGetRequest( restService.getApiUrl() + userUrl);
        parser.printPretty(response);
    }

    public void addPerson() {
        System.out.println("Insert person's data (q to quit)");
        Person person = new Person();
        String userInput;

        System.out.println("First name: ");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        person.setFirstName(userInput);

        System.out.println("Last name: ");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        person.setLastName(userInput);

        System.out.println("Email: ");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        person.setEmail(userInput);

        String response = restService.addPerson(person);
        parser.printPretty(response);
    }

    public void addCar() {
        System.out.println("Insert car's details (q to quit)");
        Car car = new Car();
        String userInput;

        System.out.println("Make: ");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        car.setMake(userInput);

        System.out.println("Model: ");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        car.setModel(userInput);

        while (true) {
            System.out.println("Production year: ");
            userInput = scanner.nextLine();
            if (userInput.charAt(0) == 'q') {
                return;
            }
            try {
                car.setProductionYear(Integer.parseInt(userInput));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Production year must be a number");
            }
        }

        System.out.println("Fuel: (DIESEL, PETROL, PETROL_LPG, ELECTRIC)");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        car.setFuel(userInput);

        System.out.println("Engine volume: ");
        userInput = scanner.nextLine();
        if (userInput.charAt(0) == 'q') {
            return;
        }
        car.setEngineVolume(userInput);

        String response = restService.addCar(car);
        parser.printPretty(response);
    }

    public void addExistingCarToPerson() {
        Person person;
        Car car;
        String userInput;
        int personId, carId;

        while (true) {
            System.out.println("Choose person id");
            parser.printPretty(restService.getAllPersons());

            while (true) {
                userInput = scanner.nextLine();
                try {
                    personId = Integer.parseInt(userInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Person's id must be a number");
                }
            }

            person = parser.getPerson(restService.getPerson(personId));
            if (person == null) {
                System.out.println("Choose existing person's id");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Choose car id");
            parser.printPretty(restService.getAllCars());
            while (true) {
                userInput = scanner.nextLine();
                try {
                    carId = Integer.parseInt(userInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Car's id must be a number");
                }
            }

            car = parser.getCar(restService.getCar(carId));
            if (car == null) {
                System.out.println("Choose existing car's id");
            } else {
                break;
            }
        }

        person.addCar(car);
        String response = restService.updatePerson(person);
        parser.printPretty(response);
    }

    public void removeCarFromPerson() {
        Person person;
        Car car;
        String userInput;
        int personId, carId;

        while (true) {
            parser.printPretty(restService.getAllPersons());
            System.out.println("Choose person id (q to quit)");
            while (true) {
                userInput = scanner.nextLine();
                if (userInput.charAt(0) == 'q')
                    return;
                try {
                    personId = Integer.parseInt(userInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Person's id must be a number");
                }
            }
            person = parser.getPerson(restService.getPerson(personId));
            if (person == null) {
                System.out.println("Choose existing person's id");
            } else {
                break;
            }
        }

        List<Car> cars = person.getCars();
        List<Integer> availableIds = new ArrayList<>();
        if (!cars.isEmpty()) {
            for (Car c : cars) {
                availableIds.add(c.getId());
            }
            String idList = availableIds.stream().map(String::valueOf).collect(Collectors.joining(", "));
            while (true) {
                parser.printCars(cars);
                System.out.println("Choose car id to remove (" + idList + ")");
                while (true) {
                    userInput = scanner.nextLine();
                    if (userInput.charAt(0) == 'q')
                        return;
                    try {
                        carId = Integer.parseInt(userInput);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Car's id must be a number");
                    }
                }

                car = parser.getCar(restService.getCar(carId));
                if (car == null) {
                    System.out.println("Choose existing car's id");
                } else {
                    break;
                }
            }

            person.removeCar(car);
            String response = restService.updatePerson(person);
            parser.printPretty(response);

        } else {
            System.out.println("No cars to remove");
        }
    }

    public void deletePerson() {
        this.printPersonList();
        System.out.println("Select person id to remove");
        String input;
        int personId;

        while (true) {
            input = scanner.nextLine();
            if (input.charAt(0) == 'q')
                return;
            try {
                personId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Must provide number");
            }
        }

        String response = restService.deletePerson(personId);
        parser.printPretty(response);
    }

    public void deleteCar() {
        this.getCarList();
        System.out.println("Select car id to remove");
        String input;
        int carId;

        while (true) {
            input = scanner.nextLine();
            if (input.charAt(0) == 'q')
                return;
            try {
                carId = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Must provide number");
            }
        }

        String response = restService.deleteCar(carId);
        parser.printPretty(response);
    }

    public void run() {
        outer:
        while (true) {
            printMenu();
            String userInput = scanner.nextLine();
            switch (userInput.charAt(0)) {
                case '1':
                    printPersonList();
                    break;
                case '2':
                    getCarList();
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
                    removeCarFromPerson();
                    break;
                case '7':
                    deletePerson();
                    break;
                case '8':
                    deleteCar();
                    break;
                case '9':
                    userCustomGetRequest();
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

    public static void main(String[] args) {
        ApplicationMenu app = new ApplicationMenu();
        app.run();
    }

}
