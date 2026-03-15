package com.deliveryslots.controller;

import com.deliveryslots.dto.response.ReservationResponse;
import com.deliveryslots.enums.ReservationStatus;
import com.deliveryslots.exception.GlobalExceptionHandler;
import com.deliveryslots.exception.SlotExhaustedException;
import com.deliveryslots.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReservationController.class)
@Import(GlobalExceptionHandler.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void create_shouldReturn201WhenValid() throws Exception {
        
        // Preparar la respuesta que el servicio retornaría
        ReservationResponse response = new ReservationResponse();
        response.setId(1L);
        response.setWindowId(1L);
        response.setZoneId(1L);
        response.setCommune("Huechuraba");
        response.setCustomerName("Juan");
        response.setCustomerAddress("Av. 123");
        response.setStatus(ReservationStatus.CONFIRMED);

        when(reservationService.reserve(any())).thenReturn(response);

        String body = """
            {"windowId":1,"zoneId":1,"commune":"Huechuraba","customerName":"Juan","customerAddress":"Av. 123"}
            """;

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Juan"))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void create_shouldReturn400WhenFieldsEmpty() throws Exception {
        String body = """
            {"windowId":null,"zoneId":null,"commune":"","customerName":"","customerAddress":""}
            """;

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturn409WhenSlotExhausted() throws Exception {
        when(reservationService.reserve(any()))
                .thenThrow(new SlotExhaustedException("Ventana agotada"));

        String body = """
            {"windowId":1,"zoneId":1,"commune":"Huechuraba","customerName":"Juan","customerAddress":"Av. 123"}
            """;

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Ventana agotada"));
    }
}