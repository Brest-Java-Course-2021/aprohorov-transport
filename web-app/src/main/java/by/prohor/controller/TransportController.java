package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    private List<Transport> transportAdd() {
        transports = new ArrayList<>();
        transports.add(new Transport(TypeTransport.TROLLEY, FuelType.GASOLINE, "2356 AB-1", 52, Date.valueOf("2020-02-12"), 1));
        transports.add(new Transport(TypeTransport.BUS, FuelType.ELECTRIC, "3423 AB-1", 67, Date.valueOf("2020-02-12"), 6));
        transports.add(new Transport(TypeTransport.TRAM, FuelType.DIESEL, "4354 AB-1", 15, Date.valueOf("2020-02-12"), 2));
        transports.add(new Transport(TypeTransport.TROLLEY, FuelType.GAS, "3542 AB-1", 97, Date.valueOf("2020-02-12"), 8));
        return transports;
    }
}
