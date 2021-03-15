package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfiguration.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"})})
public class RouteDaoImplTestINTEGR {

    @Autowired
    private RouteDao routeDao;


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
    void update_whenRouteDoesNotExistInDb() {
        Route route = new Route();
        assertEquals(0, (int) routeDao.update(route));
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
    public void findByNumberRoute_whenRouteDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findByNumberRoute(0));
    }

    @Test
    public void findById_whenRouteWithParametersIsCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findById(route.getRouteId()), route);
    }

    @Test
    public void findById_whenValueInMethodNull_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findById(null));
    }

    @Test
    public void findById_whenRouteDoesNotExistsInDb_thenThrowEmptyResultDataAccessException() {
        assertThrows(EmptyResultDataAccessException.class, () -> routeDao.findById(0));
    }

}