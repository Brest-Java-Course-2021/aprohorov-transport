package by.prohor.service.impl;

import by.prohor.dao.jdbc.RouteDaoImpl;
import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {


    @InjectMocks
    private RouteServiceImpl routeServiceImpl;

    @Mock
    private RouteDaoImpl routeDaoImpl;

    @Test
    void testGetAll_whenDaoReturnAllRoutesIsEmpty() {
        when(routeDaoImpl.getAll()).thenReturn(Collections.emptyList());
        assertNotNull(routeServiceImpl.getAll());
        assertEquals(0, routeServiceImpl.getAll().size());
    }

    @Test
    void testGetAll_whenDaoReturnAllRoutesWithSizeThree() {
        List<Route> routes = Arrays.asList(
                new Route(12, 4.9, 12, 123),
                new Route(2, 5.9, 22, 1243),
                new Route(9, 12.5, 44, 23));
        when(routeDaoImpl.getAll()).thenReturn(routes);
        assertEquals(3, routeServiceImpl.getAll().size());
    }

    @Test
    void testSave_whenRouteIsCorrect() {
        Route route = new Route(9, 0.9, 9, 9);
        when(routeDaoImpl.save(route)).thenReturn(route);
        assertEquals(routeServiceImpl.save(route), route);
    }

    @Test
    void testSave_save_whenObjectRouteIsEmpty_thenThrowDataIntegrityViolationException() {
        Route route = new Route();
        when(routeDaoImpl.save(route)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> routeServiceImpl.save(route));
    }

    @Test
    void testDelete_whenRouteIsCorrect() {
        when(routeDaoImpl.delete(any(Integer.class))).thenReturn(1);
        assertEquals(1, routeServiceImpl.delete(4));
    }

    @Test
    void testDelete_whenRouteNotExistInDb() {
        when(routeDaoImpl.delete(0)).thenReturn(0);
        assertEquals(0, routeServiceImpl.delete(0));
    }

    @Test
    void testUpdate_whenRouteIsCorrect() {
        Route route = new Route(9, 0.9, 9, 9);
        route.setNumberRoute(5);
        when(routeDaoImpl.update(any(Route.class))).thenReturn(1);
        assertEquals(1, routeServiceImpl.update(route));
    }

    @Test
    void testUpdate_whenRouteNotExistInDb() {
        when(routeDaoImpl.update(any(Route.class))).thenReturn(0);
        assertEquals(0, routeServiceImpl.update(new Route()));
    }

    @Test
    void testFindById_whenRouteExistInDb() {
        when(routeDaoImpl.findById(any(Integer.class))).thenReturn(new Route());
        assertNotNull(routeServiceImpl.findById(5));
    }

    @Test
    void testFindById_whenRouteExistInDb_thenThrowEmptyResultDataAccessException() {
        when(routeDaoImpl.findById(any(Integer.class))).thenThrow(EmptyResultDataAccessException.class);
        assertThrows(EmptyResultDataAccessException.class, () -> routeServiceImpl.findById(0));
    }

    @Test
    void searchOnPageRoute_whenMethodParametersIsCorrect() {
        List<RouteDto> routes = List.of(new RouteDto(9, 0.9, 9, 9, 2),
                new RouteDto(13, 13.9, 13, 13, 2));
        when(routeDaoImpl.searchOnPageRoute(any(String.class), any(Integer.class), any(Integer.class))).thenReturn(routes);
        assertEquals(2, routeServiceImpl.searchOnPageRoute("NUMBER_ROUTE", 5, 13).size());
    }

    @Test
    void getAllWithNumberOfVehicles_whenDaoReturnAllRoutes() {
        List<RouteDto> routes = List.of(new RouteDto());
        when(routeDaoImpl.getAllWithNumberOfVehicles()).thenReturn(routes);
        assertEquals(1, routeServiceImpl.getAllWithNumberOfVehicles().size());
    }
}