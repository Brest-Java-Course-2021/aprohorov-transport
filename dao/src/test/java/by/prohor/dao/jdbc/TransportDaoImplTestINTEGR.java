package by.prohor.dao.jdbc;

import by.prohor.dao.TransportDao;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
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
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
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
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", capacityNull, Date.valueOf("2020-02-12"), 5);
        assertThrows(DataIntegrityViolationException.class, () -> transportDao.save(transport));
    }

    @Test
    void save_whenTransportIsTheSameInDb_thenThrowsDuplicateEntityInDbException() {
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        assertNotNull(transport);
        assertThrows(DuplicateEntityInDbException.class, () -> transportDao.save(transport));
    }

    @Test
    void delete_whenTransportCorrect() {
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
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
    void update_whenTransportWithCorrectParameters() {
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Integer transportId = transport.getTransportId();
        transport.setTransportType(TransportType.TRAM);
        transport.setFuelType(FuelType.ELECTRIC);
        transport.setRegisterNumber("1111 AZ-1");
        transport.setCapacity(100);
        transport.setDateOfManufacture(Date.valueOf("2012-12-12"));
        assertTrue(transportDao.update(transport) > 0);
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test
    void update_whenRegisterNumberHasAlreadyInDbAndEqualsUpdatedTransport() {
        Transport transportSaveInDb = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Transport duplicateRegisterNumber = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5);
        duplicateRegisterNumber.setTransportType(TransportType.BUS);
        duplicateRegisterNumber.setFuelType(FuelType.ELECTRIC);
        duplicateRegisterNumber.setTransportId(transportSaveInDb.getTransportId());
        assertEquals(1, transportDao.update(duplicateRegisterNumber));
        assertEquals(duplicateRegisterNumber, transportDao.findById(duplicateRegisterNumber.getTransportId()));
    }

    @Test
    void update_whenRegisterNumberHasAlreadyInDbAndNotEqualsUpdatedTransportButHeExistsInDb_thenThrowsDuplicateEntityInDbException() {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Transport transportSaveInDb = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "1111 AA-1", 45, Date.valueOf("2020-02-12"), 5));
        transportSaveInDb.setRegisterNumber("2356 AB-1");
        assertThrows(DuplicateEntityInDbException.class, () -> transportDao.update(transportSaveInDb));
    }

    @Test
    void update_whenRegisterNumberHasAlreadyInDbAndNotEqualsUpdatedTransportButHeNotExistsInDb() {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, Date.valueOf("2020-02-12"), 5));
        Transport transportSaveInDb = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "1111 AA-1", 45, Date.valueOf("2020-02-12"), 5));
        transportSaveInDb.setRegisterNumber("1212 AS-1");
        assertEquals(1, transportDao.update(transportSaveInDb));
    }

    @Test
    void update_whenTransportDoesNotExistInDb_thenThrowEmptyResultDataAccessException() {
        Transport transport = new Transport();
        assertThrows(EmptyResultDataAccessException.class, () -> transportDao.update(transport));
    }

    @Test
    void update_whenValueInMethodNull_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> transportDao.update(null));
    }

    @Test
    void findByNumberRoute_whenTransportWithParametersIsCorrectWithOutNumberRoute() {
        int sizeBefore = transportDao.getAll().size();
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2020-02-12"), null));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, Date.valueOf("2020-02-12"), null));
        int sizeAfter = transportDao.getAll().size();
        assertNotEquals(sizeAfter, sizeBefore);
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
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2020-02-12"), 5));
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

    @Test
    void getAllNumberRoutes() {
        List<Route> numberRoutes = transportDao.getAllNumberRoutes();
        assertNotNull(numberRoutes);
        assertTrue(numberRoutes.size() > 0);
    }

    @ParameterizedTest
    @MethodSource("checkValue")
    void searchOnPageTransportByDate_whenTransportWithParameters(String dateBefore, String dateAfter, int result) {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, Date.valueOf("2002-02-12"), 5));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, Date.valueOf("2006-02-12"), 5));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "9999 AB-1", 45, Date.valueOf("2004-02-12"), 5));
        assertEquals(result, transportDao.searchOnPageTransportByDate(Date.valueOf(dateBefore), Date.valueOf(dateAfter)).size());
    }

    private static Stream<Arguments> checkValue() {
        return Stream.of(
                Arguments.of("2001-02-12", "2008-02-12", 3),
                Arguments.of("2005-02-12", "2009-02-12", 1),
                Arguments.of("2010-02-12", "2011-02-12", 0),
                Arguments.of("2001-02-12", "2005-02-12", 2),
                Arguments.of("2021-02-12", "2021-02-12", 0),
                Arguments.of("2021-02-12", "2008-02-12", 0)
        );
    }
}