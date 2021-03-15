package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfiguration.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"})})
class TransportDaoImplTestINTEGR {

    @Autowired
    private TransportDao transportDao;


    @Test
    void getAll() {
        List<Transport> transports = transportDao.getAll();
        assertNotNull(transports);
        assertTrue(transports.size() > 0);
    }

    @Test
    void save_whenTransportCorrect() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        assertNotNull(transport);
        Integer transportId = transport.getTransportId();
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test
    void save_whenValueInMethodNull() {
        assertThrows(NullPointerException.class, () -> transportDao.save(null));
    }

    @Test
    void save_whenOneParametersIsNull_thenThrowsDataIntegrityViolationException() {
        Integer capacityNull = null;
        Transport transport = new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", capacityNull, Date.valueOf("2020-02-12"), 5);
        assertThrows(DataIntegrityViolationException.class, () -> transportDao.save(transport));
    }

    @Test
    void save_whenTransportIsTheSameInDb_thenThrowsDuplicateEntityInDbException() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        assertNotNull(transport);
        assertThrows(DuplicateEntityInDbException.class, () -> transportDao.save(transport));
    }

    @Test
    void delete_whenTransportCorrect() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        assertEquals(transportDao.findById(transportId), transport);
        assertEquals(1, (int) transportDao.delete(transportId));
        assertEquals(0, (int) transportDao.delete(transportId));
    }

    @Test
    void delete_whenTransportDoesNotExistInDb() {
        assertEquals(0, (int) transportDao.delete(0));
    }

    @Test()
    void delete_whenValueInMethodNull() {
        assertEquals(0, (int) transportDao.delete(null));
    }

    @Test
    @Disabled
    void update_whenTransportWithCorrectParameters() {
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

    @Test
    void update_whenRegisterNumberHasAlreadyInDb_thenThrowsDuplicateEntityInDbException() {
        transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Transport duplicateRegisterNumber = new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5);
        duplicateRegisterNumber.setRegisterNumber("2356 AB-1");
        assertThrows(DuplicateEntityInDbException.class, () -> transportDao.update(duplicateRegisterNumber));
    }

    @Test
    void update_whenTransportDoesNotExistInDb() {
        Transport transport = new Transport();
        assertEquals(0, (int) transportDao.update(transport));
    }

    @Test
    void update_whenValueInMethodNull_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transportDao.update(null));
    }

    @Test
    @Disabled
    void findByNumberRoute_whenTransportWithParametersIsCorrect() {
        // Todo do it real
        Integer numberRoute = 6;
        transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));
        transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));

        assertEquals(2, transportDao.findByNumberRoute(numberRoute).size());
    }

    @Test
    void findByNumberRoute_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        List<Transport> byNumberRoute = transportDao.findByNumberRoute(null);
        assertTrue(byNumberRoute.isEmpty());
    }

    @Test
    void findByNumberRoute_whenTransportDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        List<Transport> byNumberRoute = transportDao.findByNumberRoute(0);
        assertTrue(byNumberRoute.isEmpty());
    }

    @Test
    void findById_whenTransportWithParametersIsCorrect() {
        Transport transport = transportDao.save(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test
    void findById_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> transportDao.findById(null));
    }

    @Test
    void findById_whenTransportDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> transportDao.findById(0));
    }
}