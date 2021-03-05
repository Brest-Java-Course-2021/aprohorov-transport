package by.prohor.dao;

import by.prohor.dao.common.TransportParkDao;
import by.prohor.model.Route;

/**
 * Created by Artsiom Prokharau 23.02.2021
 */


public interface RouteDao extends TransportParkDao<Route> {

    Route findByNumberRoute(Integer numberRoute);
}
