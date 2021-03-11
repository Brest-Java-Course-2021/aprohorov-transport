package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("transports", transportService.getAll());
        model.addAttribute("heading", "Transport");
        return "transport";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransport(@PathVariable int id) {
        transportService.delete(id);
        return "redirect:/route";
    }

    @PostMapping("/update")
    public String updateTransportInDb(@ModelAttribute Transport transport) {
        transportService.update(transport);
        return "redirect:/route";
    }

    @PostMapping("/new")
    public String createTransportInDb(@ModelAttribute Transport transport) {
        transportService.save(transport);
        return "redirect:/route";
    }

    @GetMapping("/edit/{id}")
    public String editTransport(Model model, @PathVariable int id) {
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("current_transport", transportService.findById(id));
        return "transport_edit";
    }

    @GetMapping("/create/{id}")
    public String createTransport(Model model, @PathVariable int id) {
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        Transport transport = new Transport();
        transport.setNumberRoute(id);
        model.addAttribute("current_transport", transport);
        return "transport_edit";
    }


    @GetMapping("/create")
    public String createTransport(Model model) {
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("current_transport", new Transport());
        return "transport_edit";
    }

    @GetMapping("/route/{id}")
    public String getTransportWithNumberRoute(Model model, @PathVariable int id) {
        model.addAttribute("heading", "Transport route " + id);
        model.addAttribute("transports", transportService.findByNumberRoute(id));
        model.addAttribute("new transport", id);
        return "transport";
    }
}
