package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfiguration.class)
public class TransportDaoImplTest {

    @Autowired
    private TransportDao transportDao;


    @Test
    public void getAll() {
        List<Transport> transports = transportDao.getAll();
        assertNotNull(transports);
        assertTrue(transports.size() > 0);
    }

    @Test
    public void save_whenTransportCorrect() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        assertNotNull(transport);
        Integer transportId = transport.getTransportId();
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test(expected = NullPointerException.class)
    public void save_whenValueInMethodNull() {
        transportDao.save(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void save_whenOneParametersIsNull() {
        Integer capacityNull = null;
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", capacityNull, Date.valueOf("2020-02-12"), 5));
        transportDao.save(transport);
    }

    @Test(expected = DuplicateEntityInDbException.class)
    public void save_whenTransportIsTheSameInDb() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        transportDao.save(transport);
        assertNotNull(transport);
        transportDao.save(transport);
    }

    @Test
    public void delete_whenTransportCorrect() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        assertEquals(transportDao.findById(transportId), transport);
        assertEquals(1, (int) transportDao.delete(transportId));
        assertEquals(0, (int) transportDao.delete(transportId));
    }

    @Test
    public void delete_whenTransportDoesNotExistInDb() {
        assertEquals(0, (int) transportDao.delete(0));
    }

    @Test()
    public void delete_whenValueInMethodNull() {
        assertEquals(0, (int) transportDao.delete(null));
    }

    @Test
    public void update_whenTransportWithCorrectParameters() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        transport.setTransportType(TypeTransport.TRAM);
        transport.setFuelType(FuelType.ELECTRIC);
        transport.setRegisterNumber("1111 AZ-1");
        transport.setCapacity(100);
        transport.setDateOfManufacture(Date.valueOf("2012-12-12"));
        transport.setNumberRoute(999);
        assertTrue(transportDao.update(transport) > 0);
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test(expected = DuplicateEntityInDbException.class)
    public void update_whenNumberRouteHasAlreadyInDb() {
        routeDao.save(new Route(20, 0.9, 9, 9));
        Route route = routeDao.save(new Route(9, 0.9, 9, 9));
        route.setNumberRoute(20);
        route.setLength(20.0);
        route.setLapTime(20);
        route.setNumberOfStops(20);
        routeDao.update(route);
    }

    @Test
    public void update_whenRouteDoesNotExistInDb() {
        Route route = new Route();
        assertEquals(0, (int) routeDao.update(route));
    }

    @Test(expected = NullPointerException.class)
    public void update_whenValueInMethodNull() {
        assertEquals(0, (int) routeDao.update(null));
    }

    @Test
    public void findByNumberRoute_whenRouteWithParametersIsCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findByNumberRoute(3), route);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByNumberRoute_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        routeDao.findByNumberRoute(null);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByNumberRoute_whenRouteDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        routeDao.findByNumberRoute(0);
    }

    @Test
    public void findById_whenRouteWithParametersIsCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findById(route.getRouteId()), route);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findById_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        routeDao.findById(null);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findById_whenRouteDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        routeDao.findById(0);
    }
}