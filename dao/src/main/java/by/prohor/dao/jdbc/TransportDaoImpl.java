package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */

@Repository
public class TransportDaoImpl implements TransportDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportDaoImpl.class);


    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    @Value("${transport.getAll}")
    private String getAllSql;

    @Value("${transport.findById}")
    private String findByIdSql;

    @Value("${transport.delete}")
    private String deleteSql;

    @Value("${transport.update}")
    private String updateSql;

    @Value("${transport.findByNumberRoute}")
    private String getTransportsFindByNumberRouteSql;

    @Value("${transport.check}")
    private String checkSql;

    @Value("${transport.allNumberRoutes}")
    private String getAllNumberRoutesSql;

    @Value("${transport.searchByDate}")
    private String searchByDateSql;

    public TransportDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TRANSPORT")
                .usingGeneratedKeyColumns("TRANSPORT_ID");
    }


    @Override
    public List<Transport> getAll() {
        LOGGER.debug("Get all transports from DB");
        List<Transport> transports = jdbcTemplate.query(getAllSql, new TransportRowMapper());
        LOGGER.info("Get all transports and their numbers is {}", transports.size());
        return transports;
    }

    @Override
    public Transport save(Transport model) {
        LOGGER.debug("Save transport with parameters: transport type = {}, " + "fuel type = {}, " + "register number = {}, capacity = {}, date of manufacture = {}, number route = {}",
                model.getTransportType(), model.getFuelType(), model.getRegisterNumber(), model.getCapacity(), model.getDateOfManufacture(), model.getNumberRoute());
        if (!isRegisterNumberUnique(model)) {
            LOGGER.warn("Transport with the same register number ( {} ) already exists in Db", model.getRegisterNumber());
            throw new DuplicateEntityInDbException("Transport with the same register number already exists in Db");
        }
        Number number = simpleJdbcInsert.executeAndReturnKey(mapTransport(model));
        model.setTransportId(number.intValue());
        LOGGER.info("Save transport which have id => {}", model.getTransportId());
        return model;
    }

    @Override
    public Transport findById(Integer transportId) {
        LOGGER.debug("Find transport from DB with id {}", transportId);
        Transport transport = jdbcTemplate.queryForObject(findByIdSql, new TransportRowMapper(), transportId);
        LOGGER.info("Found transport with id {}", transportId);
        return transport;
    }

    @Override
    public Integer delete(Integer transportId) {
        LOGGER.debug("Delete transport  from DB with id => {} ", transportId);
        int delete = jdbcTemplate.update(deleteSql, transportId);
        LOGGER.info("Transport with id {} deleted from DB in quantity {}", transportId, delete);
        return delete;
    }

    @Override
    public Integer update(Transport model) {
        LOGGER.debug("Update transport with id {} in DB", model.getNumberRoute());
        if (!isRegisterNumberUniqueForUpdateTransport(model)) {
            LOGGER.warn("Transport with the same register number ( {} ) already exists in Db", model.getRegisterNumber());
            throw new DuplicateEntityInDbException("Transport with the same register number already exists in Db");
        }
        int update = jdbcTemplate.update(updateSql, String.valueOf(model.getTransportType()), String.valueOf(model.getFuelType()), model.getRegisterNumber(), model.getCapacity(), model.getDateOfManufacture(), model.getNumberRoute(), model.getTransportId());
        LOGGER.info("Transport with id {} updated in BD in quantity {}", model.getNumberRoute(), update);
        return update;
    }


    @Override
    public List<Transport> findByNumberRoute(Integer numberRoute) {
        LOGGER.debug("Get all transports from DB with number route => " + numberRoute);
        List<Transport> transports = jdbcTemplate.query(getTransportsFindByNumberRouteSql, new TransportRowMapper(), numberRoute);
        LOGGER.info("Get all transports  with number route => {} and their numbers is {}", numberRoute, transports.size());
        return transports;
    }

    @Override
    public List<Route> getAllNumberRoutes() {
        LOGGER.debug("Get all number routes from DB");
        List<Route> allNumberRoutes = jdbcTemplate.query(getAllNumberRoutesSql, new BeanPropertyRowMapper<>(Route.class));
        LOGGER.info("Get all number routes and their numbers is {}", allNumberRoutes.size());
        return allNumberRoutes;
    }

    @Override
    public List<Transport> searchOnPageTransportByDate(Date dateBefore, Date dateAfter) {
        LOGGER.debug("Find all transports from DB with date of manufacture");
        List<Transport> allTransportsByDate = jdbcTemplate.query(searchByDateSql, new TransportRowMapper(), dateBefore, dateAfter);
        LOGGER.info("Found all transports by date and their numbers is {}", allTransportsByDate.size());
        return allTransportsByDate;
    }

    private Map<String, Object> mapTransport(Transport model) {
        Map<String, Object> mapRoute = new HashMap<>();
        mapRoute.put("TRANSPORT_TYPE", String.valueOf(model.getTransportType()));
        mapRoute.put("FUEL_TYPE", String.valueOf(model.getFuelType()));
        mapRoute.put("REGISTER_NUMBER", model.getRegisterNumber());
        mapRoute.put("CAPACITY", model.getCapacity());
        mapRoute.put("DATE_OF_MANUFACTURE", model.getDateOfManufacture());
        mapRoute.put("NUMBER_ROUTE", model.getNumberRoute());
        return mapRoute;
    }

    private boolean isRegisterNumberUnique(Transport model) {
        return jdbcTemplate.query(checkSql, new TransportRowMapper(), model.getRegisterNumber()).isEmpty();
    }

    private boolean isRegisterNumberUniqueForUpdateTransport(Transport model) {
        Transport transportInDb = findById(model.getTransportId());
        if (model.getRegisterNumber().equals(transportInDb.getRegisterNumber())) {
            return true;
        }
        return isRegisterNumberUnique(model);
    }

    private class TransportRowMapper implements RowMapper<Transport> {

        @Override
        public Transport mapRow(ResultSet resultSet, int i) throws SQLException {
            Transport transport = new Transport();

            transport.setTransportId(resultSet.getInt("TRANSPORT_ID"));
            transport.setTransportType(TransportType.valueOf(resultSet.getString("TRANSPORT_TYPE")));
            transport.setFuelType(FuelType.valueOf(resultSet.getString("FUEL_TYPE")));
            transport.setRegisterNumber(resultSet.getString("REGISTER_NUMBER"));
            transport.setCapacity(resultSet.getInt("CAPACITY"));
            transport.setDateOfManufacture(resultSet.getDate("DATE_OF_MANUFACTURE"));
            transport.setNumberRoute(resultSet.getInt("NUMBER_ROUTE"));

            return transport;
        }
    }
}
