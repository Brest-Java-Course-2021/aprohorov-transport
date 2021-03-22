package by.prohor.service;

import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.common.TransportParkService;

import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */


public interface RouteService extends TransportParkService<Route> {

    Route findByNumberRoute(Integer numberRoute);

    List<RouteDto> searchOnPageRoute(String search, Integer start, Integer end);

    List<RouteDto> getAllWithNumberOfVehicles();
}
