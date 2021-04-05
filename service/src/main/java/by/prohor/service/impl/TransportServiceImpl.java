package by.prohor.service.impl;

import by.prohor.dao.TransportDao;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final TransportDao transportDao;

    public TransportServiceImpl(TransportDao transportDao) {
        this.transportDao = transportDao;
    }


    @Override
    public Transport findById(Integer Id) {
        return transportDao.findById(Id);
    }

    @Override
    public List<Transport> getAllTransport() {
        return transportDao.getAllTransport();
    }

    @Override
    public Transport save(Transport model) {
        return transportDao.save(model);
    }

    @Override
    public Integer delete(Integer id) {
        return transportDao.delete(id);
    }

    @Override
    public Integer update(Transport model) {
        return transportDao.update(model);
    }

    @Override
    public List<Transport> findAllTransportWithNumberRoute(Integer numberRoute) {
        return transportDao.findByNumberRoute(numberRoute);
    }

    @Override
    public List<Route> getAllAvailableNumberRoutes() {
        return transportDao.getAllAvailableNumberRoutes();
    }

    @Override
    public List<Transport> searchTransportByDate(LocalDate dateBefore, LocalDate dateAfter) {
        return transportDao.searchOnPageTransportByDate(dateBefore, dateAfter);
    }
}
