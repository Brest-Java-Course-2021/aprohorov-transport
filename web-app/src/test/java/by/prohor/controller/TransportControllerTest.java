package by.prohor.controller;

import by.prohor.model.Transport;
import by.prohor.model.type.FuelType;
import by.prohor.model.type.TransportType;
import by.prohor.service.TransportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

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
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, new Date(2002 - 2 - 12));
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
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, new Date(2002 - 2 - 12));
        mockMvc.perform(post("/transport/new")
                .flashAttr("transport", transport))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/transport"));
//                .andExpect(view().name("redirect:route/{" + transport.getNumberRoute() + "}" ));
    }

    @Test
    void createTransportInDb_whenObjectTransportIsCorrect() throws Exception {
        Transport transport = new Transport(TransportType.TROLLEY, FuelType.GASOLINE, "7777 AB-1", 45, new Date(2002 - 2 - 12));
        transport.setNumberRoute(4);
        mockMvc.perform(post("/transport/new")
                .flashAttr("transport", transport)
                .flashAttr("id", transport.getNumberRoute()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:route/{numberRoute}"));
    }

    @Test
    void editTransport() {
    }

    @Test
    void createTransport() {
    }

    @Test
    void testCreateTransport() {
    }

    @Test
    void getTransportWithNumberRoute() {
    }

    @Test
    void searchTransportByDate() {
    }
}