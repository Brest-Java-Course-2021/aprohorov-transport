package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Artsiom Prokharau 22.02.2021
 */

@Repository
public class RouteDaoImpl implements RouteDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDaoImpl.class);


    @Autowired
    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;
    private RowMapper<Route> rowMapper;

    @Value("${route.getAll}")
    private String getAllSql;

    @Value("${route.findByNumberRoute}")
    private String findByNumberRouteSql;

    @Value("${route.findById}")
    private String findByIdSql;

    @Value("${route.delete}")
    private String deleteSql;

    @Value("${route.update}")
    private String updateSql;

    public RouteDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        rowMapper = new BeanPropertyRowMapper<>(Route.class);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ROUTE")
                .usingGeneratedKeyColumns("ROUTE_ID");
    }

    @Override
    public List<Route> getAll() {
        LOGGER.debug("Get all routes from DB");
        List<Route> routes = jdbcTemplate.query(getAllSql, rowMapper);
        LOGGER.info("Get all routes and their numbers is {}", routes.size());
        return routes;
    }

    @Override
    public Route findByNumberRoute(Integer numberRoute) {
        LOGGER.debug("Find route from DB with number route {}", numberRoute);
        Route route = jdbcTemplate.queryForObject(findByNumberRouteSql, rowMapper, numberRoute);
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
    public Integer delete(Integer id) {
        LOGGER.debug("Delete route from DB by id => {} ", id);
        int delete = jdbcTemplate.update(deleteSql, id);
        LOGGER.info("Route with id => {} deleted from DB in quantity {}", id, delete);
        return delete;
    }

    @Override
    public Integer update(Route model) {
        LOGGER.debug("Update route with  Id => {} in DB", model.getRouteId());
        int update = jdbcTemplate.update(updateSql, model.getNumberRoute(), model.getLength(), model.getLapTime(), model.getNumberOfStops(), model.getRouteId());
        LOGGER.info("Route with id => {} updated in BD", model.getRouteId());
        return update;
    }

    @Override
    public Route findById(Integer id) {
        LOGGER.debug("Find route from DB with id {}", id);
        Route route = jdbcTemplate.queryForObject(findByIdSql, rowMapper, id);
        LOGGER.info("Found route with id {} ", id);
        return route;
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


