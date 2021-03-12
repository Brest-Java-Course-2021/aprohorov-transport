package by.prohor.service.impl;

import by.prohor.dao.jdbc.RouteDaoImpl;
import by.prohor.model.Route;
import by.prohor.service.RouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RouteServiceImplTest {


    @InjectMocks
    private RouteServiceImpl routeServiceImpl;

    @Mock
    private RouteDaoImpl routeDaoImpl;

    @Test
    public void testFindByNumberRoute_thenDaoReturnRouteCorrect() {
        Route route =new Route(9, 0.9, 9, 9);
        when(routeDaoImpl.findByNumberRoute(9)).thenReturn(route);
        assertNotNull(routeServiceImpl.findByNumberRoute(9));
        assertEquals(route, routeServiceImpl.findByNumberRoute(9));
    }

    @Test
    public void testFindByNumberRoute_thenDaoReturnNull() {
        when(routeDaoImpl.findByNumberRoute(9)).thenReturn(null);
        assertNull(routeServiceImpl.findByNumberRoute(9));
    }

    @Test
    public void testGetAll_whenReturnAllRoutesIsEmpty() {
        assertNotNull(routeServiceImpl.getAll());
    }

    @Test
    public void testGetAll_whenReturnAllRoutesWithSizeThree() {
        List<Route> routes = Arrays.asList(
                new Route(12, 4.9, 12, 123),
                new Route(2, 5.9, 22, 1243),
                new Route(9, 12.5, 44, 23));
        when(routeDaoImpl.getAll()).thenReturn(routes);
        assertEquals(3,routeServiceImpl.getAll().size());
    }

    @Test
    public void testSave() {
        when(routeDaoImpl.save(null)).thenReturn();
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testFindById() {
    }
}