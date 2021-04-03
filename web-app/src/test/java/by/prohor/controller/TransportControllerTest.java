package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import by.prohor.service.TransportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TransportController.class)
class TransportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransportService transportService;


    @Test
    void getTransport() throws Exception {
        mockMvc.perform(get("/transport")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("transport"));
    }

    @Test
    void deleteTransport_whenIdIsCorrect() throws Exception {
        mockMvc.perform(get("/transport/delete/{id}", 5)
        ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/route"));
    }


    @Test
    void updateTransport_whenObjectTransportIsEmpty() throws Exception {
        Transport transport = new Transport();
        mockMvc.perform(post("/transport/update")
                .flashAttr("transport", transport))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("transport_edit"));
    }

    @Test
    void updateTransport_whenObjectTransportIsCorrect() throws Exception {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2002,2,12));
        transport.setNumberRoute(4);
        mockMvc.perform(post("/transport/update")
                .flashAttr("transport", transport))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:route/{numberRoute}"));
    }

    @Test
    void updateTransport_whenObjectTransportIsCorrectButNumberRouteNull() throws Exception {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2002,2,12));
        transport.setNumberRoute(null);
        mockMvc.perform(post("/transport/update")
                .flashAttr("transport", transport))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/transport"));
    }

    @Test
    void createTransportInDb_whenObjectTransportIsEmpty() throws Exception {
        Transport transport = new Transport();
        mockMvc.perform(post("/transport/new")
                .flashAttr("transport", transport))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("transport_edit"));
    }

    @Test
    void createTransportInDb_whenObjectTransportIsCorrectButNumberRouteNull() throws Exception {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2002,2,12));
        transport.setNumberRoute(null);
        mockMvc.perform(post("/transport/new")
                .flashAttr("transport", transport))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/transport"));
    }

    @Test
    void createTransportInDb_whenObjectTransportIsCorrect() throws Exception {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2002,2,12));
        transport.setNumberRoute(4);
        mockMvc.perform(post("/transport/new")
                .flashAttr("transport", transport)
                .flashAttr("id", transport.getNumberRoute()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:route/{numberRoute}"));
    }

    @Test
    void editTransport_whenIdIsExist() throws Exception {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, LocalDate.of(2002,2,12));
        transport.setTransportId(5);
        when(transportService.findById(any(Integer.class))).thenReturn(transport);
        mockMvc.perform(get("/transport/edit/{id}", transport.getTransportId()))
                .andExpect(status().isOk())
                .andExpect(view().name("transport_edit"));
    }

    @Test
    void createTransportById_thenIdIsCorrect() throws Exception {
        mockMvc.perform(get("/transport/create/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("transport_edit"));
    }

    @Test
    void createTransportById_whenIdIsNotCorrect_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/transport/create/{id}", " "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTransport() throws Exception {
        mockMvc.perform(get("/transport/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("transport_edit"));
    }

    @Test
    void getTransportWithNumberRoute_whenIdIsCorrect() throws Exception {
        mockMvc.perform(get("/transport/route/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("transport"));
    }

    @Test
    void getTransportWithNumberRoute_whenIdIsNotCorrect_thenReturnBadRequest() throws Exception {
        mockMvc.perform(get("/transport/route/{id}", " "))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("badRequestSearch")
    void searchTransportByDate_whenSomeParametersIsEmpty_thenReturnBadRequest(String dateBefore, String dateAfter) throws Exception {
        mockMvc.perform(get("/transport/search")
                .param("dateBefore", dateBefore)
                .param("dateAfter", dateAfter))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchTransportByDate_whenParametersIsCorrect() throws Exception {
        mockMvc.perform(get("/transport/search")
                .param("dateBefore", String.valueOf(LocalDate.of(2020,2,12)))
                .param("dateAfter", String.valueOf(LocalDate.of(2020,2,18))))
                .andExpect(status().isOk())
                .andExpect(view().name("transport"));
    }

    private static Stream<Arguments> badRequestSearch() {
        return Stream.of(
                Arguments.of(" ", " "),
                Arguments.of("sda", " "),
                Arguments.of("  ", " sda"),
                Arguments.of("121212 ", " 121212"),
                Arguments.of("102-102-102 ", " 102-102-102"));
    }
}