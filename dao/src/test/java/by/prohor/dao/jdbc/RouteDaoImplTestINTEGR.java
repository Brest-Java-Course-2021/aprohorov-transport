package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.dao.TransportDao;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.model.dto.RouteDto;
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
public class RouteDaoImplTestINTEGR {

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private TransportDao transportDao;


    @Test
    void getAll() {
        List<Route> routes = routeDao.getAll();
        assertNotNull(routes);
        assertTrue(routes.size() > 0);
    }

    @Test
    void save_whenRouteCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertNotNull(route);
        assertEquals(3, (int) route.getNumberRoute());
        assertEquals(routeDao.findByNumberRoute(3), route);
    }

    @Test
    void save_whenValueInMethodNull_thenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> routeDao.save(null));
    }

    @Test
    void save_whenOneParametersIsNull_thenThrowDataIntegrityViolationException() {
        Integer lapTime = null;
        assertThrows(DataIntegrityViolationException.class, () -> routeDao.save(new Route(3, 12.3, lapTime, 8)));
    }

    @Test
    void save_whenRouteIsTheSameInDb_thenThrowDuplicateEntityInDbException() {
        Route route = new Route(3, 12.3, 26, 8);
        routeDao.save(route);
        assertNotNull(route);
        assertThrows(DuplicateEntityInDbException.class, () -> routeDao.save(route));
    }

    @Test
    void save_whenObjectRouteIsEmpty_thenThrowDataIntegrityViolationException() {
        Route route = new Route();
        assertThrows(DataIntegrityViolationException.class, () -> routeDao.save(route));
    }

    @Test
    public void delete_whenRouteCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findByNumberRoute(3), route);
        assertEquals(1, (int) routeDao.delete(route.getNumberRoute()));
        assertEquals(0, (int) routeDao.delete(route.getNumberRoute()));
    }

    @Test
    void delete_whenRouteDoesNotExistInDb() {
        assertEquals(0, (int) routeDao.delete(0));
    }

    @Test()
    void delete_whenValueInMethodNull() {
        assertEquals(0, (int) routeDao.delete(null));
    }

    @Test
    void update_whenRouteWithCorrectParameters() {
        Route route = routeDao.save(new Route(9, 0.9, 9, 9));
        route.setNumberRoute(20);
        route.setLength(20.0);
        route.setLapTime(20);
        route.setNumberOfStops(20);
        assertTrue(routeDao.update(route) > 0);
        assertEquals(routeDao.findByNumberRoute(20), route);
    }

    @Test
    void update_whenNumberRouteHasAlreadyInDb_thenThrowDuplicateEntityInDbException() {
        routeDao.save(new Route(20, 0.9, 9, 9));
        Route routeDuplicate = routeDao.save(new Route(9, 0.9, 9, 9));
        routeDuplicate.setNumberRoute(20);
        routeDuplicate.setLength(20.0);
        routeDuplicate.setLapTime(20);
        routeDuplicate.setNumberOfStops(20);
        assertThrows(DuplicateEntityInDbException.class, () -> routeDao.update(routeDuplicate));
    }


    @Test
    void update_whenRouteDoesNotExistInDb_thenThrowEmptyResultDataAccessException() {
        Route route = new Route();
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.update(route));
    }

    @Test
    void update_whenValueInMethodNull_thenThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> routeDao.update(null));
    }

    @Test
    void findByNumberRoute_whenRouteWithParametersIsCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findByNumberRoute(3), route);
    }

    @Test
    void findByNumberRoute_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findByNumberRoute(null));
    }

    @Test
    void findByNumberRoute_whenRouteDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findByNumberRoute(0));
    }

    @Test
    void findById_whenRouteWithParametersIsCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findById(route.getRouteId()), route);
    }

    @Test
    public void findById_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findById(null));
    }

    @Test
    void findById_whenRouteDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findById(0));
    }

    @Test
    void searchOnPageRoute_whenValueInMethodNull_thenReturnEmptyList() {
        List<RouteDto> routes = routeDao.searchOnPageRoute("LENGTH", null, null);
        assertEquals(0, routes.size());
    }

    @Test
    void searchOnPageRoute_whenCorrectParametersInMethod() {
        routeDao.save(new Route(15, 23.5, 45, 12));
        routeDao.save(new Route(17, 12.3, 26, 8));
        String search = "NUMBER_ROUTE";
        Integer start = 15;
        Integer end = 17;
        List<RouteDto> routes = routeDao.searchOnPageRoute(search, start, end);
        assertEquals(2, routes.size());
    }

    @ParameterizedTest
    @MethodSource("checkValue")
    void searchOnPageRoute_whenStartMoreThanEnd(String search, Integer start, Integer end, Integer result) {
        routeDao.save(new Route(15, 150.5, 900, 300));
        routeDao.save(new Route(17, 130.3, 700, 245));
        List<RouteDto> routes = routeDao.searchOnPageRoute(search, start, end);
        assertEquals(result, routes.size());
    }

    @Test
    void getAllWithNumberOfVehicles_whenCorrectParameters() {
        Integer numberRoute = 1;
        routeDao.save(new Route(numberRoute, 150.5, 900, 300));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "1111 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "2222 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));
        transportDao.save(new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "9999 AB-1", 45, Date.valueOf("2020-02-12"), numberRoute));
        assertEquals(3, routeDao.getAllWithNumberOfVehicles().get(0).getNumberOfVehicles());
    }

    @Test
    void getAllWithNumberOfVehicles_whenCorrectParametersInMethod_thenReturnListWithNumberOfVehiclesEmptyValue() {
        Integer numberRoute = 1;
        routeDao.save(new Route(numberRoute, 150.5, 900, 300));
        assertEquals(0, routeDao.getAllWithNumberOfVehicles().get(0).getNumberOfVehicles());
    }

    private static Stream<Arguments> checkValue() {
        return Stream.of(
                Arguments.of("NUMBER_ROUTE", 15, 17, 2),
                Arguments.of("NUMBER_ROUTE", 13, 16, 1),
                Arguments.of("LENGTH", 120, 131, 1),
                Arguments.of("LENGTH", 100, 155, 2),
                Arguments.of("LAP_TIME", 200, 600, 0),
                Arguments.of("LAP_TIME", 700, 900, 2),
                Arguments.of("NUMBER_OF_STOPS", 230, 310, 2),
                Arguments.of("NUMBER_OF_STOPS", 200, 230, 0)
        );
    }


}