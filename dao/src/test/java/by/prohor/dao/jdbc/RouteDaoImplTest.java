package by.prohor.dao.jdbc;

import by.prohor.dao.RouteDao;
import by.prohor.dao.config.DaoConfiguration;
import by.prohor.model.Route;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoConfiguration.class)
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
    public void save() {
        Route route = routeDao.save(new Route(3, 12.3, 26, 8));
        assertNotNull(route);
        assertEquals(routeDao.findByNumberRoute(3), route);
    }

    @Test
    public void delete() {
        Route route = routeDao.save(new Route(99, 12.3, 26, 8));
        assertNotNull(route);
        assertEquals(routeDao.findByNumberRoute(99), route);
        assertEquals(1, (int) routeDao.delete(route.getNumberRoute()));
    }

    @Test
    public void update() {
        Route byNumberRoute = routeDao.findByNumberRoute(7);
        byNumberRoute.setLength(99999.999);
        assertEquals(1, (int) routeDao.update(byNumberRoute));
        assertEquals(routeDao.findByNumberRoute(7).getLength(), byNumberRoute.getLength());
    }

    @Test
    public void findByNumberRoute() {
        assertNotNull(routeDao.findByNumberRoute(7));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByNumberRoute_thanEmptyResultDataAccessException() {
        routeDao.findByNumberRoute(99999);
    }

    @Test
    public void findById() {
    }
}