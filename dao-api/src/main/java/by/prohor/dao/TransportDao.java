package by.prohor.dao;

import by.prohor.dao.common.TransportParkDao;
import by.prohor.model.Route;
import by.prohor.model.Transport;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Artsiom Prokharau 23.02.2021
 */


public interface TransportDao extends TransportParkDao<Transport> {

    List<Transport> getAllTransport();

    List<Transport> findByNumberRoute(Integer numberRoute);

    List<Route> getAllAvailableNumberRoutes();

    List<Transport> searchOnPageTransportByDate(LocalDate dateBefore, LocalDate dateAfter);
}
