package by.prohor.service.impl;

import by.prohor.dao.RouteDao;
import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.RouteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

@Transactional
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteDao routeDao;

    public RouteServiceImpl(RouteDao routeDao) {
        this.routeDao = routeDao;
    }


    @Override
    public Route findByNumberRoute(Integer numberRoute) {
        return routeDao.findByNumberRoute(numberRoute);
    }

    @Override
    public List<RouteDto> searchOnPageRoute(String search, Integer start, Integer end) {
        return routeDao.searchOnPageRoute(search, start, end);
    }

    @Override
    public List<RouteDto> getAllWithNumberOfVehicles() {
        return routeDao.getAllWithNumberOfVehicles();
    }

    @Override
    public List<Route> getAll() {
        return routeDao.getAll();
    }

    @Override
    public Route save(Route model) {
        return routeDao.save(model);
    }

    @Override
    public Integer delete(Integer id) {
        return routeDao.delete(id);
    }

    @Override
    public Integer update(Route model) {
        return routeDao.update(model);
    }

    @Override
    public Route findById(Integer id) {
        return routeDao.findById(id);
    }
}
