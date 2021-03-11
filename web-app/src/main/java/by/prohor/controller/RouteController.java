package by.prohor.controller;

import by.prohor.model.Route;
import by.prohor.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
@RequestMapping(value = "/route")
public class RouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }


    @GetMapping("")
    public String allRoute(Model model) {
        List<Route> allRoutes = routeService.getAll();
        LOGGER.debug("Used {} routes for  rendering template 'route'", allRoutes.size());
        model.addAttribute("routes", allRoutes);
        LOGGER.info("View all routes and start URL method GET => ( '/' )");
        return "route";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable int id) {
        LOGGER.debug("Delete with template route with number route {}", id);
        routeService.delete(id);
        LOGGER.info("View start URL method GET => ( 'route/delete/{id}' )");
        return "redirect:/route";
    }

    @PostMapping("/update")
    public String updateRoute(@ModelAttribute Route route, BindingResult errors) {
        LOGGER.debug("Update route with template route with parameters =>{}", route);
        routeService.update(route);
        LOGGER.info("View start URL method POST => ( 'route/update/' )");
        return "redirect:/route";
    }

    @PostMapping("/new")
    public String createRoute(@ModelAttribute Route route, BindingResult errors) {
        LOGGER.debug("Create new route with template with parameters =>{}", route);
        routeService.save(route);
        LOGGER.info("View start URL method POST => ( 'route/new' )");
        return "redirect:/route";
    }

    @GetMapping("/edit/{id}")
    public String editRoute(Model model, @PathVariable Integer id) {
        LOGGER.debug("Update route with template with id =>{}", id);
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("current_route", routeService.findById(id));
        LOGGER.info("View start URL method GET => ( 'route/edit/{id}' )");
        return "route_edit";
    }

    @GetMapping("/create")
    public String createRoute(Model model) {
        LOGGER.debug("Create new route with template");
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("current_route", new Route());
        LOGGER.info("View start URL method GET => ( 'route//create' )");
        return "route_edit";
    }
}
