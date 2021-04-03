package by.prohor;

import by.prohor.dao.TransportDao;
import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Artsiom Prokharau 30.03.2021
 */

@Service
public class TransportRestService implements TransportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportRestService.class);

    String commonUrl = "http://localhost:8090/transport";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransportDao transportDao;


    @Override
    public List<Transport> findAllTransportWithNumberRoute(Integer numberRoute) {
        String indAllTransportWithNumberRouteUrl = commonUrl + "/route/{numberRoute}";
        ResponseEntity<List<Transport>> responseSearch = restTemplate.exchange(indAllTransportWithNumberRouteUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {}, "numberRoute");
        return responseSearch.getBody();
    }

    @Override
    // Todo Maybe this is not the best option
    public List<Route> getAllNumberRoutes() {
        return transportDao.getAllNumberRoutes();
    }

    @Override
    public List<Transport> searchOnPageTransportByDate(Date dateBefore, Date dateAfter) {
        String searchOnPageTransportByDateUrl = commonUrl + "/search?dateBefore={dateBefore}&dateAfter={dateAfter}";
        ResponseEntity<List<Transport>> responseSearch = restTemplate.exchange(searchOnPageTransportByDateUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, dateBefore, dateAfter);
        return responseSearch.getBody();
    }

    @Override
    public List<Transport> getAll() {
        ResponseEntity<List<Transport>> responseAllTransport = restTemplate.exchange(commonUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return responseAllTransport.getBody();
    }

    @Override
    public Transport save(Transport model) {
        String saveUrl = commonUrl + "/new";
        ResponseEntity<Transport> responseSaveTransport = restTemplate.postForEntity(saveUrl, model, Transport.class);
        return responseSaveTransport.getBody();
    }

    @Override
    public Integer delete(Integer id) {
        String deleteUrl = commonUrl + "/delete/{id}";
        return restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Integer.class, id).getBody();
    }

    @Override
    public Integer update(Transport model) {
        String updateUrl = commonUrl + "/update";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Transport> requestEntity = new HttpEntity<>(model, headers);
        return restTemplate.exchange(updateUrl,HttpMethod.PUT,requestEntity, Integer.class).getBody();
    }

    @Override
    public Transport findById(Integer id) {
        String updateUrl = commonUrl + "/edit/{id}";
        return restTemplate.getForObject(updateUrl,Transport.class, id);
    }
}

