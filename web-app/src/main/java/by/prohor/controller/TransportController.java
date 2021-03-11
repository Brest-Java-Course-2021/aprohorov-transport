package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
@RequestMapping(value = "/transport")
public class TransportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportController.class);

    public final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping("")
    public String getTransport(Model model) {
        List<Transport> allTransports = transportService.getAll();
        LOGGER.debug("Used {} transports for rendering template 'transport'", allTransports.size());
        model.addAttribute("transports", allTransports );
        model.addAttribute("heading", "Transport");
        LOGGER.info("View all transport and start URL method GET => ( '/transport' )");
        return "transport";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransport(@PathVariable int id) {
        LOGGER.debug("Delete with template transport with id => {}", id);
        transportService.delete(id);
        LOGGER.info("View start URL method GET => ( 'transport/delete/{id}' )");
        return "redirect:/route";
    }

    @PostMapping("/update")
    public String updateTransportInDb(@ModelAttribute Transport transport) {
        LOGGER.debug("Update transport with parameters =>{}", transport);
        transportService.update(transport);
        LOGGER.info("View start URL method POST => ( 'transport/update/' )");
        return "redirect:/route";
    }

    @PostMapping("/new")
    public String createTransportInDb(@ModelAttribute Transport transport) {
        LOGGER.debug("Create new route with parameters =>{}", transport);
        transportService.save(transport);
        LOGGER.info("View start URL method POST => ( 'transport/new' )");
        return "redirect:/route";
    }

    @GetMapping("/edit/{id}")
    public String editTransport(Model model, @PathVariable int id) {
        LOGGER.debug("Update transport with id =>{}", id);
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("current_transport", transportService.findById(id));
        LOGGER.info("View start URL method GET => ( 'transport/edit/{id}' )");
        return "transport_edit";
    }

    @GetMapping("/create/{id}")
    public String createTransport(Model model, @PathVariable int id) {
        LOGGER.debug("Create new transport with number route => {}", id);
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        Transport transport = new Transport();
        transport.setNumberRoute(id);
        model.addAttribute("current_transport", transport);
        LOGGER.info("View start URL method GET => ( 'route/create/{id}' )");
        return "transport_edit";
    }


    @GetMapping("/create")
    public String createTransport(Model model) {
        LOGGER.debug("Create new transport ");
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("current_transport", new Transport());
        LOGGER.info("View start URL method GET => ( 'route/create' )");
        return "transport_edit";
    }

    @GetMapping("/route/{id}")
    public String getTransportWithNumberRoute(Model model, @PathVariable int id) {
        List<Transport> transportsByNumberRoute = transportService.findByNumberRoute(id);
        LOGGER.debug("Used {} transports for rendering template 'transport' with number route => {}", transportsByNumberRoute.size(), id);
        model.addAttribute("heading", "Transport route " + id);
        model.addAttribute("transports", transportsByNumberRoute);
        model.addAttribute("new transport", id);
        LOGGER.info("View start URL method GET => ( 'transport/route/{id}' )");
        return "transport";
    }
}
