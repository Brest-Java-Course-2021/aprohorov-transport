package by.prohor.rest;

import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Artsiom Prokharau 25.03.2021
 */

@RestController
@RequestMapping(value = "/transport")
public class TransportRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportRestController.class);

    public final TransportService transportService;

    public TransportRestController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Transport>> getTransport() {
        List<Transport> allTransports = transportService.getAllTransport();
        LOGGER.debug("Used {} transports", allTransports.size());
        LOGGER.info("View all transport and start URL method GET(REST)  => ( '/transport' )");
        return new ResponseEntity<>(allTransports, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteTransport(@PathVariable int id) {
        LOGGER.debug("Delete transport with id => {}", id);
        LOGGER.info("View start URL method DELETE(REST)  => ( 'transport/delete/{id}' )");
        return new ResponseEntity<>(transportService.delete(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTransportInDb(@RequestBody Transport transport) {
        LOGGER.debug("Update transport with parameters =>{}", transport);
        LOGGER.info("View start URL method PUT(REST)  => ( 'transport/update/' )");
        return new ResponseEntity<>(transportService.update(transport), HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Transport> createTransportInDb(@RequestBody Transport transport) {
        LOGGER.debug("Create new route with parameters =>{}", transport);
        LOGGER.info("View start URL method POST(REST)  => ( 'transport/new' )");
        return new ResponseEntity<>(transportService.save(transport), HttpStatus.CREATED);
    }

    @GetMapping(value = "/route/{id}", produces = {"application/json"})
    public ResponseEntity<List<Transport>> getAllTransportWithNumberRoute(@PathVariable int id) {
        List<Transport> transportsByNumberRoute = transportService.findAllTransportWithNumberRoute(id);
        LOGGER.debug("Used {} with number route => {}", transportsByNumberRoute.size(), id);
        LOGGER.info("View start URL method GET(REST)  => ( 'transport/route/{id}' )");
        return new ResponseEntity<>(transportsByNumberRoute, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<Transport> getTransportById(@PathVariable Integer id) {
        LOGGER.debug("Find transport with id =>{}", id);
        LOGGER.info("View start URL method GET(REST) => ( 'transport/{id}' )");
        return new ResponseEntity<>(transportService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<List<Transport>> searchTransportByDate(
            @RequestParam("dateBefore") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateBefore,
            @RequestParam("dateAfter") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateAfter) {
        List<Transport> transports = transportService.searchTransportByDate(dateBefore, dateAfter);
        LOGGER.debug("Found transports by date of manufacture with parameters start => {} and end => {} In the amount of {} ", dateBefore, dateAfter, transports.size());
        LOGGER.info("View start URL method GET(REST)  => ( 'transport/search' ) with parameters start => {} and end => {}", dateBefore, dateAfter);
        return new ResponseEntity<>(transports, HttpStatus.FOUND);
    }
}
