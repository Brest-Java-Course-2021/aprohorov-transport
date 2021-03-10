package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
@RequestMapping(value="/transport")
public class TransportController {

    @Autowired
    public TransportService transportService;

    @GetMapping("")
    public String getTransport(Model model) {
        model.addAttribute("transports", transportService.getAll());
        model.addAttribute("heading", "Transport");
        return "transport";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransport(Model model, @PathVariable int id) {
        transportService.delete(id);
        return "redirect:/transport";
    }

    @PostMapping("/update")
    public String updateTransportInDb(@ModelAttribute Transport transport, BindingResult errors) {
        transportService.update(transport);
        return "redirect:/transport";
    }

    @PostMapping("/new")
    public String createTransportInDb(@ModelAttribute Transport transport, BindingResult errors) {
       transportService.save(transport);
        return "redirect:/transport";
    }

    @GetMapping("/edit/{id}")
    public String editTransport(Model model, @PathVariable Integer id) {
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("transport", transportService.findById(id));
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
        return "transport";
    }
}
