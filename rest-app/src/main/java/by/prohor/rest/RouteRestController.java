package by.prohor.rest;

import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Artsiom Prokharau 25.03.2021
 */

@RestController
@RequestMapping(value = "/route")
public class RouteRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRestController.class);

    private final RouteService routeService;

    public RouteRestController(RouteService routeService) {
        this.routeService = routeService;
    }


    @GetMapping(value = "", produces = {"application/json"})
    public ResponseEntity<List<RouteDto>> allRoute() {
        List<RouteDto> allRoutes = routeService.getAllWithNumberOfVehicles();
        LOGGER.debug("Used {} routes", allRoutes.size());
        LOGGER.info("View all routes and start URL method GET => ( '/route' )");
        return new ResponseEntity<>(allRoutes, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteRoute(@PathVariable int id) {
        LOGGER.debug("Delete route with number route => {}", id);
        LOGGER.info("View start URL method GET => ( 'route/delete/{id}' )");
        return new ResponseEntity<>(routeService.delete(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateRoute(@RequestBody Route route) {
        LOGGER.debug("Update route with parameters =>{}", route);
        LOGGER.info("View start URL method POST => ( 'route/update/' )");
        return new ResponseEntity<>(routeService.update(route), HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = {"application/json"}, produces = {"aspplication/json"})
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        LOGGER.debug("Create new with parameters =>{}", route);
        routeService.save(route);
        LOGGER.info("View start URL method POST => ( 'route/new' )");
        return new ResponseEntity<>(routeService.save(route), HttpStatus.CREATED);
    }

    @GetMapping(value = "/edit/{id}", produces = {"application/json"})
    public ResponseEntity<Route> editRoute(@PathVariable Integer id) {
        LOGGER.debug("Update route with id =>{}", id);
        LOGGER.info("View start URL method GET => ( 'route/edit/{id}' )");
        return new ResponseEntity<>(routeService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/create", produces = {"application/json"})
    public ResponseEntity<Route> createRoute() {
        LOGGER.debug("Create new route");
        LOGGER.info("View start URL method GET => ( 'route/create' )");
        return new ResponseEntity<>(new Route(), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<List<RouteDto>> searchRoute(@RequestParam("start") Integer start,
                                                      @RequestParam("end") Integer end,
                                                      @RequestParam("search") String search) {
        List<RouteDto> routes = routeService.searchOnPageRoute(search, start, end);
        LOGGER.debug("Found routes by {} with parameters start => {} and end => {} In the amount of {} ", search, start, end, routes.size());
        LOGGER.info("View start URL method GET => ( 'route/search' ) with parameters start => {} and end => {}", start, end);
        return new ResponseEntity<>(routes, HttpStatus.FOUND);
    }
}
