package pl.firma.projekt.carbase.entity;

import java.util.List;
import java.util.Objects;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Car> cars;

    public Person(){}

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        this.cars.add(car);
    }

    public boolean hasCar(Car car) {
        return this.cars.contains(car);
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cars=" + cars +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email) &&
                Objects.equals(cars, person.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, cars);
    }
}
