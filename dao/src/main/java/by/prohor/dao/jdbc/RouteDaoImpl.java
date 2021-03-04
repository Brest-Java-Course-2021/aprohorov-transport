package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Artsiom Prokharau 22.02.2021
 */

public class RouteDaoImpl implements RouteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDaoImpl.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private RowMapper<Route> rowMapper;

    public RouteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        rowMapper = new BeanPropertyRowMapper<>(Route.class);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ROUTE")
                .usingGeneratedKeyColumns("ROUTE_ID");
    }

    @Override
    public List<Route> getAll() {
        LOGGER.debug("Get all routes from DB");
        String request = "SELECT * FROM ROUTE AS R ORDER BY R.NUMBER_ROUTE";
        List<Route> routes = jdbcTemplate.query(request, rowMapper);
        LOGGER.info("Get all routes and their numbers is {}", routes.size());
        return routes;
    }

    @Override
    public Route findByNumberRoute(Integer numberRoute) {
        LOGGER.debug("Find route from DB with number route {}", numberRoute);
        String request = "SELECT * FROM ROUTE WHERE NUMBER_ROUTE = ?";
        Route route = jdbcTemplate.queryForObject(request, rowMapper, numberRoute);
        LOGGER.info("Found route with number route {} ", numberRoute);
        return route;
    }

    @Override
    public Route save(Route model) {
        LOGGER.debug("Save route with parameters: number_route = {}, " + "length = {}, lap_time = {}, number_of_stops = {}",
                model.getNumberRoute(), model.getLength(), model.getLapTime(), model.getNumberOfStops());
        Number number = simpleJdbcInsert.executeAndReturnKey(mapRoute(model));
        model.setRouteId(number.intValue());
        LOGGER.info("Save route which have number route => {}", model.getNumberRoute());
        return model;
    }

    @Override
    public Integer delete(Integer numberRoute) {
        LOGGER.debug("Delete route from DB by numberRoute => {} ", numberRoute);
        String request = "DELETE FROM ROUTE WHERE NUMBER_ROUTE = ?";
        int delete = jdbcTemplate.update(request, numberRoute);
        LOGGER.info("Route with number route {} deleted from DB in quantity {}", numberRoute, delete);
        return delete;
    }

    @Override
    public Integer update(Route model) {
        LOGGER.debug("Update route with  number route {} in DB", model.getNumberRoute());
        String request = "UPDATE ROUTE SET NUMBER_ROUTE = ?,LENGTH = ?,LAP_TIME = ?,NUMBER_OF_STOPS= ? WHERE NUMBER_ROUTE = ?";
        int update = jdbcTemplate.update(request, model.getNumberRoute(), model.getLength(), model.getLapTime(), model.getNumberOfStops(), model.getNumberRoute());
        LOGGER.info("Route with number route {} updated in BD", model.getNumberRoute());
        return update;
    }

    private Map<String, Object> mapRoute(Route model) {

        Map<String, Object> mapRoute = new HashMap<>();
        mapRoute.put("NUMBER_ROUTE", model.getNumberRoute());
        mapRoute.put("LENGTH", model.getLength());
        mapRoute.put("LAP_TIME", model.getLapTime());
        mapRoute.put("NUMBER_OF_STOPS", model.getNumberOfStops());

        return mapRoute;
    }
}


