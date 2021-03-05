package by.prohor.model;

import java.util.Objects;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */


public class Route {

    private Integer routeId;
    private Integer numberRoute;
    private Double length;
    private Integer lapTime;
    private Integer numberOfStops;
//    private List<Transport> numberOfVehicles;

    public Route() {
    }

    //Todo delete. For test controller
    public Route(Integer routeId, Integer numberRoute, Double length, Integer lapTime, Integer numberOfStops) {
        this.routeId = routeId;
        this.numberRoute = numberRoute;
        this.length = length;
        this.lapTime = lapTime;
        this.numberOfStops = numberOfStops;
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

//    public List<Transport> getNumberOfVehicles() {
//        return numberOfVehicles;
//    }

//    public void setNumberOfVehicles(List<Transport> numberOfVehicles) {
//        this.numberOfVehicles = numberOfVehicles;
//    }

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
//                Objects.equals(getNumberOfVehicles(), route.getNumberOfVehicles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRouteId(), getNumberRoute(), getLength(), getLapTime(), getNumberOfStops());//getNumberOfVehicles());
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
