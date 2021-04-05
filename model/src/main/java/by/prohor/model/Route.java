package by.prohor.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */


public class Route {

    private Integer routeId;

    @NotNull(message = "Number route should not be empty")
    @Min(value = 1, message = "Number route should be greater than 0")
    @Max(value = 9999, message = "Number route should be less than 9999")
    private Integer numberRoute;

    @NotNull(message = "Length should not be empty")
    @Min(value = 0, message = "Length should be greater than 0")
    @Max(value = 9999, message = "length should be less than 9999")
    private Double length;

    @NotNull(message = "Lap Time  should not be empty")
    @Min(value = 1, message = "Lap Time  should be greater than 0")
    @Max(value = 9999, message = "lap time should be less than 9999")
    private Integer lapTime;

    @NotNull(message = "Number of stops should not be empty")
    @Min(value = 1, message = "Number of stops should be greater than 0")
    @Max(value = 9999, message = "Number of stops should be less than 9999")
    private Integer numberOfStops;

    public Route() {
    }

    public Route(Integer numberRoute, Double length, Integer lapTime, Integer numberOfStops) {
        this.numberRoute = numberRoute;
        this.length = length;
        this.lapTime = lapTime;
        this.numberOfStops = numberOfStops;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getNumberRoute() {
        return numberRoute;
    }

    public void setNumberRoute(Integer numberRoute) {
        this.numberRoute = numberRoute;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getLapTime() {
        return lapTime;
    }

    public void setLapTime(Integer lapTime) {
        this.lapTime = lapTime;
    }

    public Integer getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return Objects.equals(getRouteId(), route.getRouteId()) &&
                Objects.equals(getNumberRoute(), route.getNumberRoute()) &&
                Objects.equals(getLength(), route.getLength()) &&
                Objects.equals(getLapTime(), route.getLapTime()) &&
                Objects.equals(getNumberOfStops(), route.getNumberOfStops());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRouteId(), getNumberRoute(), getLength(), getLapTime(), getNumberOfStops());
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", numberRoute=" + numberRoute +
                ", length=" + length +
                ", lapTime=" + lapTime +
                ", numberOfStops=" + numberOfStops +
                '}';
    }
}
