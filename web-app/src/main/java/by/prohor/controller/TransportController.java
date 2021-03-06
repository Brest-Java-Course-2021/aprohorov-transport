package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
public class TransportController {

    public List<Transport> transports = transportAdd();

    @GetMapping("/transport")
    public String getTransport(Model model) {
        model.addAttribute("transports", transports);
        return "transport";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransport(Model model, @PathVariable int id) {
        transports.remove(id);
        return "redirect:/transport";
    }

    @PostMapping("/transport/update")
    public String updateTransportinDb(@ModelAttribute Transport transport, BindingResult errors) {
        System.out.println(transport);
        //Todo update in DB
        return "redirect:/transport";
    }

    @PostMapping("/transport/new")
    public String createTransportInDb(@ModelAttribute Transport transport, BindingResult errors) {
        System.out.println(transport);
        int count = 0;
        transport.setTransportId(++count);
        transports.add(transport);
        System.out.println(transport);
        //Todo save in DB
        return "redirect:/transport";
    }

    @GetMapping("/transport_edit/{id}")
    public String editTransport(Model model, @PathVariable Integer id) {
        Transport transport = new Transport();
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("transport", transport);
        return "transport_edit";
    }

    @GetMapping("/transport/create")
    public String createTransport(Model model) {
        Transport transport = new Transport();
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("transport", transport);
        return "transport_edit";
    }

    private List<Transport> transportAdd() {
        transports = new ArrayList<>();
        int count = 0;
        transports.add(new Transport(++count, TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 52, Date.valueOf("2020-02-12"), 1));
        transports.add(new Transport(++count, TypeTransport.BUS, FuelType.ELECTRIC, "3423 AB-1", 67, Date.valueOf("2020-02-12"), 6));
        transports.add(new Transport(++count, TypeTransport.TRAM, FuelType.DIESEL, "4354 AB-1", 15, Date.valueOf("2020-02-12"), 2));
        transports.add(new Transport(++count, TypeTransport.TROLLEY, FuelType.GAS, "3542 AB-1", 97, Date.valueOf("2020-02-12"), 8));
        return transports;
    }
}
