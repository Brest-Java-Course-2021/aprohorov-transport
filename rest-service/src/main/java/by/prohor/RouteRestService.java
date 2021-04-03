package by.prohor;

import by.prohor.model.Route;
import by.prohor.model.dto.RouteDto;
import by.prohor.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    String commonUrl = "http://localhost:8090/route";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<RouteDto> searchOnPageRoute(String search, Integer start, Integer end) {
        String searchOnPageRouteUrl = commonUrl + "/search?start={start}&end={end}&search={search}";
        ResponseEntity<List<RouteDto>> responseSearch = restTemplate.exchange(searchOnPageRouteUrl, HttpMethod.GET,null, new ParameterizedTypeReference<>() {
        }, start, end,search);
        return responseSearch.getBody();
    }

    @Override
    public List<RouteDto> getAllWithNumberOfVehicles() {
        ResponseEntity<List<RouteDto>> responseSearch = restTemplate.exchange(commonUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return responseSearch.getBody();
    }

    @Override
    // Todo it does not use
    public List<Route> getAll() {
        ResponseEntity<List<Route>> responseSearch = restTemplate.exchange(commonUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return responseSearch.getBody();
    }

    @Override
    public Route save(Route model) {
        String saveUrl = commonUrl + "/new";
        ResponseEntity<Route> responseSaveRoute = restTemplate.postForEntity(saveUrl, model, Route.class);
        return responseSaveRoute.getBody();
    }

    @Override
    public Integer delete(Integer id) {
        String deleteUrl = commonUrl + "/delete/{id}";
        return restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, Integer.class, id).getBody();
    }

    @Override
    public Integer update(Route model) {
        String updateUrl = commonUrl + "/update";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Route> entity = new HttpEntity<>(model, headers);
        return restTemplate.exchange(updateUrl,HttpMethod.PUT,entity, Integer.class).getBody();
    }

    @Override
    public Route findById(Integer id) {
        String updateUrl = commonUrl + "/edit/{id}";
        return restTemplate.getForObject(updateUrl,Route.class, id);
    }
}
