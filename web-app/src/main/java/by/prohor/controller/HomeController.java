package by.prohor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Artsiom Prokharau 02.03.2021
 */

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/")
    public String startPage() {
        LOGGER.debug("Start startpage view");
        return "startpage";
    }
}
