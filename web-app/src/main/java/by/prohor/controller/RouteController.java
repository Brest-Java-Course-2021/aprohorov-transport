package by.prohor.controller;

import by.prohor.model.Route;
import by.prohor.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
@RequestMapping(value="/route")
public class RouteController {

    @Autowired
    private RouteService routeService;


    @GetMapping("")
    public String allRoute(Model model) {
        model.addAttribute("routes", routeService.getAll());
        return "route";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable int id) {
        routeService.delete(id);
        return "redirect:/route";
    }

    @PostMapping("/update")
    public String updateRouteInDb(@ModelAttribute Route route, BindingResult errors) {
        routeService.update(route);
        return "redirect:/route";
    }

    @PostMapping("/new")
    public String createRouteInDb(@ModelAttribute Route route, BindingResult errors) {
        routeService.save(route);
        return "redirect:/route";
    }

    @GetMapping("/edit/{id}")
    public String editRoute(Model model, @PathVariable Integer id) {
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("current_route", routeService.findById(id));
        return "route_edit";
    }

    @GetMapping("/create")
    public String createRoute(Model model) {
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("current_route", new Route());
        return "route_edit";
    }
}
