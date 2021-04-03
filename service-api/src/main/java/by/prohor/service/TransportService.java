package by.prohor.service;

import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.service.common.TransportParkService;

import java.util.Date;
import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

public interface TransportService extends TransportParkService<Transport> {

    List<Transport> findAllTransportWithNumberRoute(Integer numberRoute);

    List<Route> getAllNumberRoutes();

    List<Transport> searchOnPageTransportByDate(Date dateBefore, Date dateAfter);
}
