package by.prohor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
public class RouteController {


    @GetMapping("/route")
    public String route() {
        return "route";
    }
}
