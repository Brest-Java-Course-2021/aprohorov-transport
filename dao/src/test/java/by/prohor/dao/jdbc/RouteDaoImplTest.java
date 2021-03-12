package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.dao.exception.DuplicateEntityInDbException;
import by.prohor.model.Route;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfiguration.class)
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"})})
public class RouteDaoImplTest {

    @Autowired
    private RouteDao routeDao;


    @Test
    public void getAll() {
        List<Route> routes = routeDao.getAll();
        assertNotNull(routes);
        assertTrue(routes.size() > 0);
    }

    @Test
    public void save_whenRouteCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertNotNull(route);
        assertEquals(3, (int) route.getNumberRoute());
        assertEquals(routeDao.findByNumberRoute(3), route);
    }

    @Test(expected = NullPointerException.class)
    public void  save_whenValueInMethodNull() {
        routeDao.save(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void save_whenOneParametersIsNull() {
        Integer lapTime = null;
        routeDao.save(new Route(3, 12.3, lapTime, 8));
    }

    @Test(expected = DuplicateEntityInDbException.class)
    public void save_whenRouteIsTheSameInDb() {
        Route route = new Route(3, 12.3, 26, 8);
        routeDao.save(route);
        assertNotNull(route);
        routeDao.save(route);
    }

    @Test
    public void delete_whenRouteCorrect() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertEquals(routeDao.findByNumberRoute(3), route);
        assertEquals(1, (int) routeDao.delete(route.getNumberRoute()));
        assertEquals(0, (int) routeDao.delete(route.getNumberRoute()));
    }

    @Test
    public void delete_whenRouteDoesNotExistInDb() {
        assertEquals(0, (int) routeDao.delete(0));
    }

    @Test()
    public void delete_whenValueInMethodNull() {
        assertEquals(0, (int) routeDao.delete(null));
    }

    @Test
    public void update_whenRouteWithCorrectParameters() {
        Route route = routeDao.save(new Route(9, 0.9, 9, 9));
        route.setNumberRoute(20);
        route.setLength(20.0);
        route.setLapTime(20);
        route.setNumberOfStops(20);
        assertTrue(routeDao.update(route) > 0);
        assertEquals(routeDao.findByNumberRoute(20), route);
    }

    @Test(expected = DuplicateEntityInDbException.class)
    public void update_whenNumberRouteHasAlreadyInDb() {
        routeDao.save(new Route(20, 0.9, 9, 9));
        Route routeDuplicate = routeDao.save(new Route(9, 0.9, 9, 9));
        routeDuplicate.setNumberRoute(20);
        routeDuplicate.setLength(20.0);
        routeDuplicate.setLapTime(20);
        routeDuplicate.setNumberOfStops(20);
        routeDao.update(routeDuplicate);
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