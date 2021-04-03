package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.dao.TransportDao;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import by.prohor.test.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {TestConfig.class})
@JdbcTest
@PropertySource({"classpath:request.properties"})
@Transactional
class TransportDaoImplTestINTEGR {

    @Autowired
    private TransportDao transportDao;

    @Autowired
    private RouteDao routeDao;


    @Test
    void getAll_whenInDbHasTwoTransports() {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, LocalDate.of(2020,2,12)));
        List<Transport> transports = transportDao.getAllTransport();
        assertNotNull(transports);
        assertEquals(2, transports.size());
    }

    @Test
    void getAll_whenDbIsEmpty() {
        List<Transport> transports = transportDao.getAllTransport();
        assertNotNull(transports);
        assertEquals(0, transports.size());
    }

    @Test
    void save_whenTransportCorrect() {
        int sizeBefore = transportDao.getAllTransport().size();
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        assertNotNull(transport);
        int sizeAfter = transportDao.getAllTransport().size();
        assertNotEquals(sizeBefore, sizeAfter);
    }

    @Test
    void save_whenValueInMethodNull() {
        assertThrows(NullPointerException.class, () -> transportDao.save(null));
    }

    @Test
    void save_whenOneParametersIsNull_thenThrowsDataIntegrityViolationException() {
        Integer capacityNull = null;
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", capacityNull, LocalDate.of(2020,2,12));
        assertThrows(DataIntegrityViolationException.class, () -> transportDao.save(transport));
    }

    @Test
    void save_whenTransportIsTheSameInDb_thenThrowsDuplicateEntityInDbException() {
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        assertNotNull(transport);
        assertThrows(DuplicateEntityInDbException.class, () -> transportDao.save(transport));
    }

    @Test
    void delete_whenTransportCorrect() {
        int sizeBefore = transportDao.getAllTransport().size();
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        int sizeAfter = transportDao.getAllTransport().size();
        assertNotEquals(sizeBefore, sizeAfter);
        Integer transportId = transport.getTransportId();
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
        routeDao.save(new Route(3, 150.5, 900, 300));
        Transport transport = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        Integer transportId = transport.getTransportId();
        transport.setTransportType(TransportType.TRAM);
        transport.setFuelType(FuelType.ELECTRIC);
        transport.setRegisterNumber("1111 AZ-1");
        transport.setCapacity(100);
        transport.setDateOfManufacture(LocalDate.of(2012,12,12));
        transport.setNumberRoute(3);
        assertTrue(transportDao.update(transport) > 0);
        assertEquals(transportDao.findById(transportId), transport);
    }

    @Test
    void update_whenRegisterNumberHasAlreadyInDbAndEqualsUpdatedTransport() {
        routeDao.save(new Route(3, 150.5, 900, 300));
        Transport transportSaveInDb = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        Transport duplicateRegisterNumber = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12));
        duplicateRegisterNumber.setTransportType(TransportType.BUS);
        duplicateRegisterNumber.setFuelType(FuelType.ELECTRIC);
        duplicateRegisterNumber.setNumberRoute(3);
        duplicateRegisterNumber.setTransportId(transportSaveInDb.getTransportId());
        assertEquals(1, transportDao.update(duplicateRegisterNumber));
        assertEquals(duplicateRegisterNumber, transportDao.findById(duplicateRegisterNumber.getTransportId()));
    }

    @Test
    void update_whenRegisterNumberHasAlreadyInDbAndNotEqualsUpdatedTransportButHeExistsInDb_thenThrowsDuplicateEntityInDbException() {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        Transport transportSaveInDb = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "1111 AA-1", 45, LocalDate.of(2020,2,12)));
        transportSaveInDb.setRegisterNumber("2356 AB-1");
        assertThrows(DuplicateEntityInDbException.class, () -> transportDao.update(transportSaveInDb));
    }

    @Test
    void update_whenRegisterNumberHasAlreadyInDbAndNotEqualsUpdatedTransportButHeNotExistsInDb() {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 45, LocalDate.of(2020,2,12)));
        Transport transportSaveInDb = transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "1111 AA-1", 45, LocalDate.of(2020,2,12)));
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
        routeDao.save(new Route(1, 150.5, 900, 300));
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2020,2,12));
        transport.setNumberRoute(1);
        transportDao.save(transport);
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
    void getAllNumberRoutes_whenInDbHasTwoRoutes() {
        routeDao.save(new Route(1, 150.5, 900, 300));
        routeDao.save(new Route(3, 150.5, 900, 300));
        Transport transportOne = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, LocalDate.of(2020,2,12));
        transportOne.setNumberRoute(1);
        Transport transportSecond = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2020,2,12));
        transportSecond.setNumberRoute(3);

        transportDao.save(transportOne);
        transportDao.save(transportSecond);

        List<Route> numberRoutes = transportDao.getAllAvailableNumberRoutes();
        assertNotNull(numberRoutes);
        assertEquals(2, numberRoutes.size());
    }

    @ParameterizedTest
    @MethodSource("checkValue")
    void searchOnPageTransportByDate_whenTransportWithParameters(String dateBefore, String dateAfter, int result) {
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2002,2,12)));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "5555 AB-1", 45, LocalDate.of(2006,2,12)));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "9999 AB-1", 45, LocalDate.of(2004,2,12)));
        assertEquals(result, transportDao.searchOnPageTransportByDate(LocalDate.parse(dateBefore),LocalDate.parse(dateAfter)).size());
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