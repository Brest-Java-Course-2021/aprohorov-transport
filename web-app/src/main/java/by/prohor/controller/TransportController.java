package by.prohor.controller;

import by.prohor.service.TransportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
public class TransportController {


    private  TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping("/transport")
    public String getTransport() {
//        model.addAttribute("transports", transportService.getAll());
        return "transport";
    }
}
