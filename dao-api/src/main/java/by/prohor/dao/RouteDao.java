package by.prohor.dao;

import by.prohor.dao.common.TransportParkDao;
import by.prohor.model.Route;

import java.util.List;

/**
 * Created by Artsiom Prokharau 23.02.2021
 */


public interface RouteDao extends TransportParkDao<Route> {

    Route findByNumberRoute(Integer numberRoute);

    List<Route> searchOnPageRoute(String search, Integer start, Integer end);
}
