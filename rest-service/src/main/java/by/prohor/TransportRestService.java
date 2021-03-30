package by.prohor;

import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created by Artsiom Prokharau 30.03.2021
 */

@Service
public class TransportRestService implements TransportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportRestService.class);


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Transport> findByNumberRoute(Integer numberRoute) {
        return null;
    }

    @Override
    public List<Route> getAllNumberRoutes() {
        return null;
    }

    @Override
    public List<Transport> searchOnPageTransportByDate(Date dateBefore, Date dateAfter) {
        return null;
    }

    @Override
    public List<Transport> getAll() {
        ResponseEntity<List<Transport>> responseEntity = restTemplate.exchange("http://localhost:8090/transport", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

    @Override
    public Transport save(Transport model) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    public Integer update(Transport model) {
        return null;
    }

    @Override
    public Transport findById(Integer id) {
        return null;
    }
}
