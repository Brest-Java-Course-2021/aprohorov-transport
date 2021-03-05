package by.prohor.controller;

import by.prohor.model.Route;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
public class RouteController {


    public List<Route> routes = routesAdd();


    @GetMapping("/route")
    public String allRoute(Model model) {
        model.addAttribute("routes", routes);
        return "route";
    }

    @GetMapping("/delete/{numberRoute}")
    public String deleteRoute(Model model, @PathVariable int numberRoute) {
        routes.remove(numberRoute);
        return "redirect:/route";
    }

    @PostMapping("/route/update")
    public String updateRouteinDb(@ModelAttribute Route route, BindingResult errors) {

        System.out.println(route);
        //Todo update in DB
        return "redirect:/route";
    }

    @PostMapping("/route/new")
    public String createRouteInDb(@ModelAttribute Route route, BindingResult errors) {
        System.out.println(route);
        int count = 0;
        route.setRouteId(++count);
        routes.add(route);
        //Todo save in DB
        return "redirect:/route";
    }

    @GetMapping("/route_edit/{id}")
    public String editRoute(Model model, @PathVariable Integer id) {
        Route route = new Route();
        model.addAttribute("title", "Edit");
        model.addAttribute("method", "update");
        model.addAttribute("route", route);
        return "route_edit";
    }

    @GetMapping("/route/create")
    public String createRoute(Model model) {
        Route route = new Route();
        model.addAttribute("title", "Create");
        model.addAttribute("method", "new");
        model.addAttribute("route", route);
        return "route_edit";
    }

    private List<Route> routesAdd() {
        routes = new ArrayList<>();
        int count = 0;
        routes.add(new Route(++count, 1, 12.3, 26, 11));
        routes.add(new Route(++count, 6, 34.4, 43, 8));
        routes.add(new Route(++count, 2, 24.5, 11, 21));
        routes.add(new Route(++count, 8, 7.4, 55, 14));
        return routes;
    }
}
