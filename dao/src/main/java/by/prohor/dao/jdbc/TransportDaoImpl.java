package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */

public class TransportDaoImpl implements TransportDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportDaoImpl.class);

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public TransportDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("TRANSPORT")
                .usingGeneratedKeyColumns("TRANSPORT_ID");
    }

    @Override
    public List<Transport> getAll() {
        LOGGER.debug("Get all transports from DB");
        String request = "SELECT * FROM TRANSPORT AS T ORDER BY T.TRANSPORT_ID";
        List<Transport> transports = jdbcTemplate.query(request, new TransportRowMapper());
        LOGGER.info("Get all transports and their numbers is {}", transports.size());
        return transports;
    }

    @Override
    public Transport save(Transport model) {
        LOGGER.debug("Save transport with parameters: transport type = {}, " + "fuel type = {}, " + "register number = {}, capacity = {}, date of manufacture = {}, number route = {}",
                model.getTransportType(), model.getFuelType(), model.getRegisterNumber(), model.getCapacity(), model.getDateOfManufacture(), model.getNumberRoute());
        Number number = simpleJdbcInsert.executeAndReturnKey(mapTransport(model));
        model.setTransportId(number.intValue());
        LOGGER.info("Save transport which have id => {}", model.getTransportId());
        return model;
    }

    @Override
    public Transport findById(Integer transportId) {
        LOGGER.debug("Find transport from DB with id {}", transportId);
        String request = "SELECT * FROM TRANSPORT WHERE TRANSPORT_ID = ?";
        Transport transport = jdbcTemplate.queryForObject(request, new TransportRowMapper(), transportId);
        LOGGER.info("Found transport with id {}", transportId);
        return transport;
    }

    @Override
    public Integer delete(Integer transportId) {
        LOGGER.debug("Delete transport  from DB with id => {} ", transportId);
        String request = "DELETE FROM TRANSPORT WHERE TRANSPORT_ID = ?";
        int delete = jdbcTemplate.update(request, transportId);
        LOGGER.info("Transport with id {} deleted from DB in quantity {}", transportId,delete);
        return delete;
    }

    @Override
    public Integer update(Transport model) {
        LOGGER.debug("Update transport with id {} in DB", model.getNumberRoute());
        String request = "UPDATE TRANSPORT SET TRANSPORT_TYPE = ?,FUEL_TYPE = ?,REGISTER_NUMBER= ?,CAPACITY= ?, DATE_OF_MANUFACTURE = ? WHERE TRANSPORT_ID = ?";
        int update = jdbcTemplate.update(request, String.valueOf(model.getTransportType()), String.valueOf(model.getFuelType()), model.getRegisterNumber(), model.getCapacity(), model.getDateOfManufacture(), model.getTransportId());
        LOGGER.info("Transport with id {} updated in BD in quantity {}", model.getNumberRoute(),update);
        return update;
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

    private class TransportRowMapper implements RowMapper<Transport> {

        @Override
        public Transport mapRow(ResultSet resultSet, int i) throws SQLException {
            Transport transport = new Transport();

            transport.setTransportId(resultSet.getInt("TRANSPORT_ID"));
            transport.setTransportType(TypeTransport.valueOf(resultSet.getString("TRANSPORT_TYPE")));
            transport.setFuelType(FuelType.valueOf(resultSet.getString("FUEL_TYPE")));
            transport.setRegisterNumber(resultSet.getString("REGISTER_NUMBER"));
            transport.setCapacity(resultSet.getInt("CAPACITY"));
            transport.setDateOfManufacture(resultSet.getDate("DATE_OF_MANUFACTURE"));
            transport.setNumberRoute(resultSet.getInt("NUMBER_ROUTE"));

            return transport;
        }
    }
}
