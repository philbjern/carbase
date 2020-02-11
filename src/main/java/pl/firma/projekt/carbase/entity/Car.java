package pl.firma.projekt.carbase.entity;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "production_year")
    private int productionYear;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "engine_volume")
    private String engineVolume;

    public Car() {}

    public Car(String make, String model, int productionYear, String fuel, String engineVolume) {
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.fuel = fuel;
        this.engineVolume = engineVolume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(String engineVolume) {
        this.engineVolume = engineVolume;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", productionYear=" + productionYear +
                ", fuel='" + fuel + '\'' +
                ", engineVolume='" + engineVolume + '\'' +
                '}';
    }

}

