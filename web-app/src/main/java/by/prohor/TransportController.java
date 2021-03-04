package by.prohor;

import by.prohor.dao.jdbc.TransportDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Artsiom Prokharau 03.03.2021
 */

@Controller
public class TransportController {

    private TransportDaoImpl transportDao;

    public TransportController(TransportDaoImpl transportDao) {
        this.transportDao = transportDao;
    }

    @GetMapping("/transport")
    public String getTransport() {
        return "transport";
    }
}
