package by.prohor;

import by.prohor.model.Route;
import by.prohor.model.Transport;
import by.prohor.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Created by Artsiom Prokharau 30.03.2021
 */

@Service
public class TransportRestService implements TransportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportRestService.class);

    @Value("${rest.common.url}")
    String commonUrl;

    @Value("${rest.transport.url}")
    String transportUrl;

    @Value("${rest.transport.searchByDate.url}")
    String searchTransportByDateUrl;

    @Value("${rest.transport.findAllWithNumberRoute.url}")
    String findAllTransportWithNumberRouteUrl;

    @Value("${rest.transport.save.url}")
    String saveUrl;

    @Value("${rest.transport.delete.url}")
    String deleteUrl;

    @Value("${rest.transport.update.url}")
    String updateUrl;

    @Value("${rest.transport.findById.url}")
    String findByIdUrl;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransportService transportService;


    @Override
    public List<Transport> findAllTransportWithNumberRoute(Integer numberRoute) {
        ResponseEntity<List<Transport>> responseSearch = restTemplate.exchange(
                commonUrl + findAllTransportWithNumberRouteUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                numberRoute);
        return responseSearch.getBody();
    }

    @Override
    public List<Route> getAllAvailableNumberRoutes() {
        return transportService.getAllAvailableNumberRoutes();
    }

    @Override
    public List<Transport> searchTransportByDate(LocalDate dateBefore, LocalDate dateAfter) {
        ResponseEntity<List<Transport>> responseSearch = restTemplate.exchange(
                commonUrl + searchTransportByDateUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                dateBefore, dateAfter);
        return responseSearch.getBody();
    }

    @Override
    public List<Transport> getAllTransport() {
        ResponseEntity<List<Transport>> responseAllTransport = restTemplate.exchange(
                commonUrl + transportUrl,
                HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {
                });
        return responseAllTransport.getBody();
    }

    @Override
    public Transport save(Transport model) {
        ResponseEntity<Transport> responseSaveTransport = restTemplate.postForEntity(
                commonUrl + saveUrl,
                model,
                Transport.class);
        return responseSaveTransport.getBody();
    }

    @Override
    public Integer delete(Integer id) {
        return restTemplate.exchange(
                commonUrl + deleteUrl,
                HttpMethod.DELETE,
                null,
                Integer.class,
                id).getBody();
    }

    @Override
    public Integer update(Transport model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Transport> requestEntity = new HttpEntity<>(model, headers);
        return restTemplate.exchange(
                commonUrl + updateUrl,
                HttpMethod.PUT,
                requestEntity,
                Integer.class).getBody();
    }

    @Override
    public Transport findById(Integer id) {
        return restTemplate.getForObject(
                commonUrl + findByIdUrl,
                Transport.class,
                id);
    }
}


