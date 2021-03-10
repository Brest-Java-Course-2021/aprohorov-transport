package by.prohor.service;

import by.prohor.model.Transport;
import by.prohor.service.common.TransportParkService;

import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

public interface TransportService extends TransportParkService<Transport> {

    List<Transport> findByNumberRoute(Integer numberRoute);
}
