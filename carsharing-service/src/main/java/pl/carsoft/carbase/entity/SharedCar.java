package pl.carsoft.carbase.entity;

import javax.persistence.*;

@Entity
@Table(name = "carsharing")
public class SharedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long carId;

    private Long personId;

    public SharedCar() {}

    public SharedCar(Long id, Long carId, Long personId) {
        this.id = id;
        this.carId = carId;
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}
