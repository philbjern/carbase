package pl.carsoft.carbase.gateway.entity;

import java.util.List;

public class Car {

    private Long id;

    private String make;

    private String model;

    private String productionYear;

    private String engineVolume;

    private FuelType fuelType;

    private List<Person> ownerList;

    public Car() {}

    public Car(Long id, String make, String model, String productionYear, String engineVolume) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.engineVolume = engineVolume;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(String engineVolume) {
        this.engineVolume = engineVolume;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public List<Person> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<Person> ownerList) {
        this.ownerList = ownerList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", engineVolume='" + engineVolume + '\'' +
                '}';
    }

}
