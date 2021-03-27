package by.prohor.rest;

import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Artsiom Prokharau 25.03.2021
 */

@RestController
@RequestMapping(value = "/transport")
public class TransportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportController.class);

    public final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Transport>> getTransport() {
        List<Transport> allTransports = transportService.getAll();
        LOGGER.debug("Used {} transports", allTransports.size());
        LOGGER.info("View all transport and start URL method GET => ( '/transport' )");
        return new ResponseEntity<>(allTransports, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteTransport(@PathVariable int id) {
        LOGGER.debug("Delete transport with id => {}", id);
        LOGGER.info("View start URL method GET => ( 'transport/delete/{id}' )");
        return new ResponseEntity<>(transportService.delete(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTransportInDb(@ModelAttribute @Valid Transport transport) {
        LOGGER.debug("Update transport with parameters =>{}", transport);
        LOGGER.info("View start URL method POST => ( 'transport/update/' )");
        return new ResponseEntity<>(transportService.update(transport), HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Transport> createTransportInDb(@ModelAttribute @Valid Transport transport) {
        LOGGER.debug("Create new route with parameters =>{}", transport);
        LOGGER.info("View start URL method POST => ( 'transport/new' )");
        return new ResponseEntity<>(transportService.save(transport), HttpStatus.CREATED);
    }

    @GetMapping(value = "/edit/{id}", produces = {"application/json"})
    public ResponseEntity<Transport> editTransport(@PathVariable int id) {
        LOGGER.debug("Update transport with id =>{}", id);
        //Todo add in rest method getAllNumberRoutes
//        model.addAttribute("allRoutes", transportService.getAllNumberRoutes());
        LOGGER.info("View start URL method GET => ( 'transport/edit/{id}' )");
        return new ResponseEntity<>(transportService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/create/{id}", produces = {"application/json"})
    public ResponseEntity<Transport> createTransportWithId(@PathVariable int id) {
        LOGGER.debug("Create new transport with number route => {}", id);
        Transport transport = new Transport();
        transport.setNumberRoute(id);
        //Todo add in rest method getAllNumberRoutes
//        model.addAttribute("allRoutes", transportService.getAllNumberRoutes());
        LOGGER.info("View start URL method GET => ( 'route/create/{id}' )");
        return new ResponseEntity<>(transport, HttpStatus.OK);
    }


    @GetMapping(value = "/create", produces = {"application/json"})
    public ResponseEntity<Transport> createTransport() {
        LOGGER.debug("Create new transport ");
        LOGGER.info("View start URL method GET => ( 'route/create' )");
        return new ResponseEntity<>(new Transport(), HttpStatus.OK);
    }

    @GetMapping(value = "/route/{id}", produces = {"application/json"})
    public ResponseEntity<List<Transport>> getTransportWithNumberRoute(@PathVariable int id) {
        List<Transport> transportsByNumberRoute = transportService.findByNumberRoute(id);
        LOGGER.debug("Used {} with number route => {}", transportsByNumberRoute.size(), id);
        LOGGER.info("View start URL method GET => ( 'transport/route/{id}' )");
        return new ResponseEntity<>(transportsByNumberRoute, HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<List<Transport>> searchTransportByDate(@RequestParam("dateBefore") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateBefore,
                                                                 @RequestParam("dateAfter") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateAfter) {
        List<Transport> transports = transportService.searchOnPageTransportByDate(dateBefore, dateAfter);
        LOGGER.debug("Found transports by date of manufacture with parameters start => {} and end => {} In the amount of {} ", dateBefore, dateAfter, transports.size());
        LOGGER.info("View start URL method GET => ( 'transport/search' ) with parameters start => {} and end => {}", dateBefore, dateAfter);
        return new ResponseEntity<>(transports, HttpStatus.FOUND);
    }
}
