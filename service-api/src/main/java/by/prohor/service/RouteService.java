package by.prohor.service;

import by.prohor.model.Route;
import by.prohor.service.common.TransportParkService;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */


public interface RouteService extends TransportParkService<Route> {

    Route findByNumberRoute(Integer numberRoute);
}
