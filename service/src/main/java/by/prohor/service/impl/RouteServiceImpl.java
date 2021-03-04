package by.prohor.service.impl;

import by.prohor.dao.RouteDao;
import by.prohor.model.Route;
import by.prohor.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    @Autowired
    private  RouteDao routeDao;


    @Override
    public Route findByNumberRoute(Integer numberRoute) {
        return routeDao.findByNumberRoute(numberRoute);
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
}
