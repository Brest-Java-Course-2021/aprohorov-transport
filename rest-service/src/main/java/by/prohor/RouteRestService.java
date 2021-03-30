package by.prohor;

import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Artsiom Prokharau 30.03.2021
 */

@Service
public class RouteRestService implements RouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Route findByNumberRoute(Integer numberRoute) {
        return null;
    }

    @Override
    public List<RouteDto> searchOnPageRoute(String search, Integer start, Integer end) {
        return null;
    }

    @Override
    public List<RouteDto> getAllWithNumberOfVehicles() {
        return null;
    }

    @Override
    public List<Route> getAll() {
        return null;
    }

    @Override
    public Route save(Route model) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    public Integer update(Route model) {
        return null;
    }

    @Override
    public Route findById(Integer id) {
        return null;
    }
}
