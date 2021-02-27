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

    public static final Logger LOGGER= LoggerFactory.getLogger(RouteDaoImpl.class);

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
        String request = "SELECT * FROM ROUTE AS R ORDER BY R.NUMBER_ROUTE";
        return jdbcTemplate.query(request, rowMapper);
    }

    @Override
    public Route findByNumberRoute(Integer numberRoute) {
        return jdbcTemplate.queryForObject("SELECT * FROM ROUTE WHERE NUMBER_ROUTE = ?", rowMapper, numberRoute);
    }

    @Override
    public Route save(Route model) {
        Number number = simpleJdbcInsert.executeAndReturnKey(mapRoute(model));
        model.setRouteId(number.intValue());

        return model;
    }

    @Override
    public Integer delete(Integer numberRoute) {
        int delete = jdbcTemplate.update("DELETE FROM ROUTE WHERE NUMBER_ROUTE = ?", numberRoute);
        LOGGER.info("Удаление по numberRoute = {} и количество удаленных  данных = {}", numberRoute, delete);
        return delete;
    }

    @Override
    public Integer update(Route model) {
        String request = "UPDATE ROUTE SET NUMBER_ROUTE = ?,LENGTH = ?,LAP_TIME = ?,NUMBER_OF_STOPS= ? WHERE NUMBER_ROUTE = ?";
        int update = jdbcTemplate.update(request, model.getNumberRoute(), model.getLength(), model.getLapTime(), model.getNumberOfStops(), model.getNumberRoute());
        LOGGER.info("UPDATE по numberRoute = {} и количество удаленных  данных = {}", model.getNumberRoute(), update);
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


