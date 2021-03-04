package by.prohor.service;

import by.prohor.model.Transport;
import by.prohor.service.common.TransportParkService;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

public interface TransportService extends TransportParkService<Transport> {

    Transport findById(Integer transportId);
}
