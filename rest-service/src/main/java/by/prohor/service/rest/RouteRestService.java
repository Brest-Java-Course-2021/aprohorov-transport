package by.prohor.service.rest;

import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by Artsiom Prokharau 30.03.2021
 */

@Service
public class RouteRestService implements RouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteRestService.class);

    @Value("${rest.common.url}")
    String commonUrl;

    @Value("${rest.route.url}")
    String routeUrl;

    @Value("${rest.route.search.url}")
    String searchUrl;

    @Value("${rest.route.save.url}")
    String saveUrl;

    @Value("${rest.route.delete.url}")
    String deleteUrl;

    @Value("${rest.route.update.url}")
    String updateUrl;

    @Value("${rest.route.findById.url}")
    String findByIdUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<RouteDto> searchOnPageRoute(String search, Integer start, Integer end) {
        ResponseEntity<List<RouteDto>> responseSearch = restTemplate.exchange(
                commonUrl + searchUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                start, end, search);
        return responseSearch.getBody();
    }

    @Override
    public List<RouteDto> getAllWithNumberOfVehicles() {
        ResponseEntity<List<RouteDto>> responseSearch = restTemplate.exchange(
                commonUrl + routeUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return responseSearch.getBody();
    }

    @Override
    public Route save(Route model) {
        ResponseEntity<Route> responseSaveRoute = restTemplate.postForEntity(
                commonUrl + saveUrl,
                model,
                Route.class);
        return responseSaveRoute.getBody();
    }

    @Override
    public Integer delete(Integer id) {
        ResponseEntity<Integer> responseDelete = restTemplate.exchange(
                commonUrl + deleteUrl,
                HttpMethod.DELETE,
                null,
                Integer.class,
                id);
        return responseDelete.getBody();
    }

    @Override
    public Integer update(Route model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Route> entity = new HttpEntity<>(model, headers);
        ResponseEntity<Integer> responseUpdate = restTemplate.exchange(
                commonUrl + updateUrl,
                HttpMethod.PUT,
                entity,
                Integer.class);
        return responseUpdate.getBody();
    }

    @Override
    public Route findById(Integer id) {
        return restTemplate.getForObject(
                commonUrl + findByIdUrl,
                Route.class,
                id);
    }
}
