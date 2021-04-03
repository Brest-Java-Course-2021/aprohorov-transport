package by.prohor.model;

import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */


public class Transport {

    private Integer transportId;

    @NotNull(message = "Transport type should not be empty")
    private TransportType transportType;

    @NotNull(message = "Fuel type should not be empty")
    private FuelType fuelType;
    @NotBlank(message = "Register number should not be empty")
    @Pattern(regexp = "\\d{4}\\s[A-Z]{2}-[0-9]", message = "Register number is not correct")
    private String registerNumber;

    @NotNull(message = "Capacity should not be empty")
    @Min(value = 1, message = "Capacity should be greater than 0")
    @Max(value = 9999, message = "Capacity should be less than 9999")
    private Integer capacity;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Date of manufacture should not be empty")
    @PastOrPresent(message = "Incorrect value. Date can not be future")
    private LocalDate dateOfManufacture;

    private Integer numberRoute;

    public Transport() {
    }

    public Transport(TransportType transportType, FuelType fuelType, String registerNumber, Integer capacity, LocalDate dateOfManufacture) {
        this.transportType = transportType;
        this.fuelType = fuelType;
        this.registerNumber = registerNumber;
        this.capacity = capacity;
        this.dateOfManufacture = dateOfManufacture;
    }


    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
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

    public LocalDate getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(LocalDate dateOfManufacture) {
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
