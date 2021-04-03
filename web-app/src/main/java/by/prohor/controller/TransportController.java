package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
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
        List<Transport> allTransports = transportService.getAllTransport();
        LOGGER.debug("Used {} transports for rendering template 'transport'", allTransports.size());
        model.addAttribute("transports", allTransports);
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
    public String updateTransportInDb(@ModelAttribute("transport") @Valid Transport transport, BindingResult bindingResult,
                                      Model model, RedirectAttributes redirectAttributes) {
        LOGGER.debug("Update transport with parameters =>{}", transport);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Edit");
            model.addAttribute("method", "update");
            model.addAttribute("hidden", true);
            return "transport_edit";
        }
        transportService.update(transport);
        LOGGER.info("View start URL method POST => ( 'transport/update/' )");
        if (transport.getNumberRoute() != null) {
            redirectAttributes.addAttribute("numberRoute", transport.getNumberRoute());
            return "redirect:route/{numberRoute}";
        }
        return "redirect:/transport";
    }

    @PostMapping("/new")
    public String createTransportInDb(@ModelAttribute("transport") @Valid Transport transport, BindingResult bindingResult,
                                      Model model, RedirectAttributes redirectAttributes) {
        LOGGER.debug("Create new route with parameters =>{}", transport);
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Create");
            model.addAttribute("hidden", false);
            model.addAttribute("method", "new");
            return "transport_edit";
        }
        transportService.save(transport);
        LOGGER.info("View start URL method POST => ( 'transport/new' )");
        if (transport.getNumberRoute() != null) {
            redirectAttributes.addAttribute("numberRoute", transport.getNumberRoute());
            return "redirect:route/{numberRoute}";
        }
        return "redirect:/transport";
    }

    @GetMapping("/edit/{id}")
    public String editTransport(Model model, @PathVariable int id) {
        LOGGER.debug("Update transport with id =>{}", id);
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("hidden", true);
        model.addAttribute("allRoutes", transportService.getAllAvailableNumberRoutes());
        model.addAttribute("transport", transportService.findById(id));
        LOGGER.info("View start URL method GET => ( 'transport/edit/{id}' )");
        return "transport_edit";
    }

    @GetMapping("/create/{id}")
    public String createTransportById(Model model, @PathVariable int id) {
        LOGGER.debug("Create new transport with number route => {}", id);
        model.addAttribute("title", "Create");
        model.addAttribute("hidden", true);
        model.addAttribute("method", "new");
        Transport transport = new Transport();
        transport.setNumberRoute(id);
        model.addAttribute("transport", transport);
        model.addAttribute("allRoutes", transportService.getAllAvailableNumberRoutes());
        LOGGER.info("View start URL method GET => ( 'route/create/{id}' )");
        return "transport_edit";
    }


    @GetMapping("/create")
    public String createTransport(Model model) {
        LOGGER.debug("Create new transport ");
        model.addAttribute("title", "Create");
        model.addAttribute("hidden", false);
        model.addAttribute("method", "new");
        model.addAttribute("transport", new Transport());
        LOGGER.info("View start URL method GET => ( 'route/create' )");
        return "transport_edit";
    }

    @GetMapping("/route/{id}")
    public String getTransportWithNumberRoute(Model model, @PathVariable int id) {
        List<Transport> transportsByNumberRoute = transportService.findAllTransportWithNumberRoute(id);
        LOGGER.debug("Used {} transports for rendering template 'transport' with number route => {}", transportsByNumberRoute.size(), id);
        model.addAttribute("heading", "Transport route " + id);
        model.addAttribute("transports", transportsByNumberRoute);
        model.addAttribute("new transport", id);
        LOGGER.info("View start URL method GET => ( 'transport/route/{id}' )");
        return "transport";
    }

    @GetMapping("/search")
    public String searchTransportByDate(@RequestParam("dateBefore") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateBefore,
                                        @RequestParam("dateAfter") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateAfter,
                                        Model model) {
        List<Transport> transports = transportService.searchTransportByDate(dateBefore, dateAfter);
        LOGGER.debug("Found transports by date of manufacture with parameters start => {} and end => {} In the amount of {} ", dateBefore, dateAfter, transports.size());
        model.addAttribute("transports", transports);
        model.addAttribute("heading", "Transport");
        LOGGER.info("View start URL method GET => ( 'transport/search' ) with parameters start => {} and end => {}", dateBefore, dateAfter);
        return "transport";
    }
}
