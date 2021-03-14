package by.prohor.service.impl;

import by.prohor.dao.jdbc.RouteDaoImpl;
import by.prohor.model.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {


    @InjectMocks
    private RouteServiceImpl routeServiceImpl;

    @Mock
    private RouteDaoImpl routeDaoImpl;

    @Test
    void testFindByNumberRoute_thenDaoReturnRouteCorrect() {
        Route route =new Route(9, 0.9, 9, 9);
        when(routeDaoImpl.findByNumberRoute(9)).thenReturn(route);
        assertNotNull(routeServiceImpl.findByNumberRoute(9));
        assertEquals(route, routeServiceImpl.findByNumberRoute(9));
    }

    @Test
    void testFindByNumberRoute_thenDaoReturnNull() {
        when(routeDaoImpl.findByNumberRoute(9)).thenReturn(null);
        assertNull(routeServiceImpl.findByNumberRoute(9));
    }

    @Test
    void testGetAll_whenReturnAllRoutesIsEmpty() {
        assertNotNull(routeServiceImpl.getAll());
    }

    @Test
    void testGetAll_whenReturnAllRoutesWithSizeThree() {
        List<Route> routes = Arrays.asList(
                new Route(12, 4.9, 12, 123),
                new Route(2, 5.9, 22, 1243),
                new Route(9, 12.5, 44, 23));
        when(routeDaoImpl.getAll()).thenReturn(routes);
        assertEquals(3,routeServiceImpl.getAll().size());
    }

    @Test
    void testSave() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testFindById() {
    }
}