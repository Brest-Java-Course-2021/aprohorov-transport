package by.prohor.controller;

import by.prohor.model.Route;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
@RequestMapping(value="/route")
public class RouteController {


    public List<Route> routes = routesAdd();
    public static int count = 0;

    @GetMapping("")
    public String allRoute(Model model) {
        model.addAttribute("routes", routes);
        return "route";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(Model model, @PathVariable int id) {
        routes.remove(id - 1);
        return "redirect:/route";
    }

    @PostMapping("/update")
    public String updateRouteinDb(@ModelAttribute Route route, BindingResult errors) {
        System.out.println(route);
        //Todo update in DB
        return "redirect:/route";
    }

    @PostMapping("/new")
    public String createRouteInDb(@ModelAttribute Route route, BindingResult errors) {
        System.out.println(route);
        route.setRouteId(++count);
        routes.add(route);
        System.out.println(route);
        //Todo save in DB
        return "redirect:/route";
    }

    @GetMapping("/edit/{id}")
    public String editRoute(Model model, @PathVariable Integer id) {
        Route route = new Route();
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("route", route);
        model.addAttribute("route_edit", routes.get(id - 1));
        return "route_edit";
    }

    @GetMapping("/create")
    public String createRoute(Model model) {
        Route route = new Route();
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("route", route);
        // Todo test auto
        model.addAttribute("route_edit", new Route());
        return "route_edit";
    }

    private List<Route> routesAdd() {
        routes = new ArrayList<>();
        routes.add(new Route(++count, 1, 12.3, 26, 11));
        routes.add(new Route(++count, 6, 34.4, 43, 8));
        routes.add(new Route(++count, 2, 24.5, 11, 21));
        routes.add(new Route(++count, 8, 7.4, 55, 14));
        return routes;
    }
}
