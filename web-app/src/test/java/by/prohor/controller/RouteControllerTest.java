package by.prohor.controller;

import by.prohor.model.Route;
import by.prohor.service.RouteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;


    @Test
    void allRoute() throws Exception {
        mockMvc.perform(get("/route")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("route"));
    }

    @Test
    void deleteRoute() throws Exception {
        mockMvc.perform(get("/route/delete/{id}", 5)
        ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/route"));
    }

    @ParameterizedTest
    @MethodSource("badParameters")
    void updateRoute_whenObjectRouteHasBadValue(String numberRoute, String length, String lapTime, String numberOfStops) throws Exception {
        mockMvc.perform(post("/route/update")
                .param("numberRoute", numberRoute)
                .param("length", length)
                .param("lapTime", lapTime)
                .param("numberOfStops", numberOfStops))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("route_edit"));
    }

    @ParameterizedTest
    @MethodSource("correctParameters")
    void updateRoute_whenObjectRouteHasCorrectValue(String numberRoute, String length, String lapTime, String numberOfStops) throws Exception {
        mockMvc.perform(post("/route/update")
                .param("numberRoute", numberRoute)
                .param("length", length)
                .param("lapTime", lapTime)
                .param("numberOfStops", numberOfStops))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/route"));
    }

    @ParameterizedTest
    @MethodSource("badParameters")
    void createRoute_whenObjectRouteHasBadValue(String numberRoute, String length, String lapTime, String numberOfStops) throws Exception {
        mockMvc.perform(post("/route/new")
                .param("numberRoute", numberRoute)
                .param("length", length)
                .param("lapTime", lapTime)
                .param("numberOfStops", numberOfStops))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("route_edit"));
    }

    @ParameterizedTest
    @MethodSource("correctParameters")
    void createRoute_whenObjectRouteHasCorrectValue(String numberRoute, String length, String lapTime, String numberOfStops) throws Exception {
        mockMvc.perform(post("/route/update")
                .param("numberRoute", numberRoute)
                .param("length", length)
                .param("lapTime", lapTime)
                .param("numberOfStops", numberOfStops))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/route"));
    }

    @Test
    void editRoute_whenIdIsExist() throws Exception {
        Route route = new Route(1, 1.0, 1, 1);
        route.setRouteId(5);
        when(routeService.findById(any(Integer.class))).thenReturn(route);
        mockMvc.perform(get("/route/edit/{id}", route.getRouteId()))
                .andExpect(status().isOk())
                .andExpect(view().name("route_edit"));
    }

    @Test
    void testCreateRoute() throws Exception {
        mockMvc.perform(get("/route/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("route_edit"));
    }

    @Test
    void searchRoute_whenParametersIsCorrect() throws Exception {
        mockMvc.perform(get("/route/search")
                .param("start", "1")
                .param("end", "14")
                .param("search", "NUMBER_ROUTE"))
                .andExpect(status().isOk())
                .andExpect(view().name("route"));
    }

    @ParameterizedTest
    @MethodSource("badRequestSearch")
    void searchRoute_whenParametersIsEmpty(String start, String end, String search) throws Exception {
        mockMvc.perform(get("/route/search")
                .param("start", start)
                .param("end", end)
                .param("search", search))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> badRequestSearch() {
        return Stream.of(
                Arguments.of("sad", "sds", " "),
                Arguments.of("sda", " ", " "),
                Arguments.of(" ", " ", "NUMBER_ROUTE"));
    }

    private static Stream<Arguments> badParameters() {
        return Stream.of(
                Arguments.of("-1", "-1", "-1", "-1"),
                Arguments.of(" ", " ", " ", " "),
                Arguments.of(" ", "a", " ", "a"));
    }

    private static Stream<Arguments> correctParameters() {
        return Stream.of(
                Arguments.of("1", "1", "1", "1"),
                Arguments.of("32", "13", "64", "26"),
                Arguments.of("8", "26", "43", "11"));
    }


}