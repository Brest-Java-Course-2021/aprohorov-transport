package by.prohor.model;

import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;

import java.sql.Date;
import java.util.Objects;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */


public class Transport {

    private Integer transportId;
    private TypeTransport transportType;
    private FuelType fuelType;
    private String registerNumber;
    private Integer capacity;
    private Date dateOfManufacture;
    private Integer numberRoute;

    public Transport() {
    }

    //Todo delete. For test controller
    public Transport(Integer transportId, TypeTransport transportType, FuelType fuelType, String registerNumber, Integer capacity, Date dateOfManufacture, Integer numberRoute) {
        this.transportId = transportId;
        this.transportType = transportType;
        this.fuelType = fuelType;
        this.registerNumber = registerNumber;
        this.capacity = capacity;
        this.dateOfManufacture = dateOfManufacture;
        this.numberRoute = numberRoute;
    }

    public Transport(TypeTransport transportType, FuelType fuelType, String registerNumber, Integer capacity, Date dateOfManufacture, Integer numberRoute) {
        this.transportType = transportType;
        this.fuelType = fuelType;
        this.registerNumber = registerNumber;
        this.capacity = capacity;
        this.dateOfManufacture = dateOfManufacture;
        this.numberRoute = numberRoute;
    }


    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public TypeTransport getTransportType() {
        return transportType;
    }

    public void setTransportType(TypeTransport transportType) {
        this.transportType = transportType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Integer getNumberRoute() {
        return numberRoute;
    }

    public void setNumberRoute(Integer numberRoute) {
        this.numberRoute = numberRoute;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "transportId=" + transportId +
                ", type=" + transportType +
                ", fuelType=" + fuelType +
                ", capacity=" + capacity +
                ", dateOfManufacture=" + dateOfManufacture +
                ", routeId=" + numberRoute +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transport)) return false;
        Transport transport = (Transport) o;
        return Objects.equals(getTransportId(), transport.getTransportId()) &&
                getTransportType() == transport.getTransportType() &&
                getFuelType() == transport.getFuelType() &&
                Objects.equals(getRegisterNumber(), transport.getRegisterNumber()) &&
                Objects.equals(getCapacity(), transport.getCapacity()) &&
                Objects.equals(getDateOfManufacture(), transport.getDateOfManufacture()) &&
                Objects.equals(numberRoute, transport.numberRoute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransportId(), getTransportType(), getFuelType(), getRegisterNumber(), getCapacity(), getDateOfManufacture(), numberRoute);
    }


}
