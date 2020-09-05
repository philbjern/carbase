package pl.carsoft.carbase.gateway.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Person {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Date registrationDate;

    private List<Car> carsList;

    public Person() {}

    public Person(String firstName, String lastName, String email, Date registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.registrationDate = registrationDate;
    }

//    public Person(String firstName, String lastName, String email) {
//        this(firstName, lastName, email, LocalDateTime.now());
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Car> getCarsList() {
        return carsList;
    }

    public void setCarsList(List<Car> carsList) {
        this.carsList = carsList;
    }

    public void addCar(Car car) {
        this.carsList.add(car);
    }

    public void updateCar(Car car) {
        for (int i = 0; i < carsList.size(); i++) {
            if (carsList.get(i).getId().equals(car.getId())) {
                carsList.remove(i);
                carsList.add(i, car);
            }
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", registeredOn=" + registrationDate +
                ", carsList=" + carsList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email) &&
                Objects.equals(registrationDate, person.registrationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, registrationDate);
    }

}