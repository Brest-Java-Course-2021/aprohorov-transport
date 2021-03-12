package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfiguration.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"})})
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
    @Ignore
    public void update_whenTransportWithCorrectParameters() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        transport.setTransportType(TypeTransport.TRAM);
        transport.setFuelType(FuelType.ELECTRIC);
        transport.setRegisterNumber("1111 AZ-1");
        transport.setCapacity(100);
        transport.setDateOfManufacture(Date.valueOf("2012-12-12"));
        // Todo not created in DB
        transport.setNumberRoute(999);
        assertTrue(transportDao.update(transport) > 0);
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test(expected = DuplicateEntityInDbException.class)
    public void update_whenRegisterNumberHasAlreadyInDb() {
        transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Transport duplicateRegisterNumber = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        duplicateRegisterNumber.setRegisterNumber("2356 AB-1");
        transportDao.update(duplicateRegisterNumber);
    }

    @Test
    public void update_whenTransportDoesNotExistInDb() {
        Transport transport = new Transport();
        assertEquals(0, (int) transportDao.update(transport));
    }

    @Test(expected = NullPointerException.class)
    public void update_whenValueInMethodNull() {
        assertEquals(0, (int) transportDao.update(null));
    }

    @Test
    @Ignore
    public void findByNumberRoute_whenTransportWithParametersIsCorrect() {
        // Todo do it real
        Integer numberRoute = 6;
        transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));
        transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));

        assertEquals(2,transportDao.findByNumberRoute(numberRoute).size());
    }

    @Test
    public void findByNumberRoute_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        List<Transport> byNumberRoute = transportDao.findByNumberRoute(null);
        assertTrue(byNumberRoute.isEmpty());
    }

    @Test
    public void findByNumberRoute_whenTransportDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        List<Transport> byNumberRoute = transportDao.findByNumberRoute(0);
        assertTrue(byNumberRoute.isEmpty());
    }

    @Test
    public void findById_whenTransportWithParametersIsCorrect() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findById_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        transportDao.findById(null);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findById_whenTransportDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
       transportDao.findById(0);
    }
}