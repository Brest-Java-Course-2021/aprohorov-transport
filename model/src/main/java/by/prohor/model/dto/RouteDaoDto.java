package by.prohor.model.dto;

/**
 * Created by Artsiom Prokharau 20.03.2021
 */

public class RouteDaoDto {

    private Integer routeId;
    private Integer numberRoute;
    private Double length;
    private Integer lapTime;
    private Integer numberOfStops;
    private Integer numberOfVehicles;

    public RouteDaoDto() {
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

    public Integer getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(Integer numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    @Override
    public String toString() {
        return "RouteDaoDto{" +
                "routeId=" + routeId +
                ", numberRoute=" + numberRoute +
                ", length=" + length +
                ", lapTime=" + lapTime +
                ", numberOfStops=" + numberOfStops +
                ", numberOfVehicles=" + numberOfVehicles +
                '}';
    }
}
