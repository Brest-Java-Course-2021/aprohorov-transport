package by.prohor.controller;

import by.prohor.dao.jdbc.RouteDaoImpl;
import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
        List<RouteDto> allRoutes = routeService.getAllWithNumberOfVehicles();
        LOGGER.debug("Used {} routes for rendering template 'route'", allRoutes.size());
        model.addAttribute("routes", allRoutes);
        LOGGER.info("View all routes and start URL method GET => ( '/route' )");
        return "route";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable int id) {
        LOGGER.debug("Delete with template route with number route => {}", id);
        routeService.delete(id);
        LOGGER.info("View start URL method GET => ( 'route/delete/{id}' )");
        return "redirect:/route";
    }

    @PostMapping("/update")
    public String updateRoute(@ModelAttribute("route") @Valid Route route, BindingResult errors,Model model) {
        LOGGER.debug("Update route with parameters =>{}", route);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit");
            model.addAttribute("method", "update");
            return "route_edit";
        }
        routeService.update(route);
        LOGGER.info("View start URL method POST => ( 'route/update/' )");
        return "redirect:/route";
    }

    @PostMapping("/new")
    public String createRoute(@ModelAttribute("route") @Valid Route route, BindingResult errors,Model model) {
        LOGGER.debug("Create new with parameters =>{}", route);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create");
            model.addAttribute("method", "new");
            return "route_edit";
        }
        routeService.save(route);
        LOGGER.info("View start URL method POST => ( 'route/new' )");
        return "redirect:/route";
    }

    @GetMapping("/edit/{id}")
    public String editRoute(Model model, @PathVariable Integer id) {
        LOGGER.debug("Update route with id =>{}", id);
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("route", routeService.findById(id));
        LOGGER.info("View start URL method GET => ( 'route/edit/{id}' )");
        return "route_edit";
    }

    @GetMapping("/create")
    public String createRoute(Model model) {
        LOGGER.debug("Create new route");
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("route", new Route());
        LOGGER.info("View start URL method GET => ( 'route/create' )");
        return "route_edit";
    }

    @GetMapping("/search")
    public String searchRoute(@RequestParam("start") Integer start,
                              @RequestParam("end") Integer end,
                              @RequestParam("search") String search, Model model) {
        List<RouteDto> routes = routeService.searchOnPageRoute(search, start, end);
        LOGGER.debug("Found routes by {} with parameters start => {} and end => {} In the amount of {} " , search,start,end,routes.size());
        model.addAttribute("routes", routes);
        LOGGER.info("View start URL method GET => ( 'route/search' ) with parameters start => {} and end => {}",start,end);
        return "route";
    }
}
