package by.prohor.dao;

import by.prohor.dao.common.TransportParkDao;
import by.prohor.model.Route;
import by.prohor.model.Transport;

import java.util.Date;
import java.util.List;

/**
 * Created by Artsiom Prokharau 23.02.2021
 */


public interface TransportDao extends TransportParkDao<Transport> {

    List<Transport> findByNumberRoute(Integer numberRoute);

    List<Route> getAllNumberRoutes();

    List<Transport> searchOnPageTransportByDate(Date dateBefore, Date dateAfter);
}
