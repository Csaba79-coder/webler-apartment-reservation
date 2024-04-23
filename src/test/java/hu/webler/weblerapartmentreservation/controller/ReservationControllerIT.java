package hu.webler.weblerapartmentreservation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.service.ApartmentService;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.service.InvoiceService;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.service.ReservationService;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {ReservationController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Reservation controller test")
public class ReservationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserService userService;

    @MockBean
    private ApartmentService apartmentService;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    @DisplayName("Given empty list when renderAllReservation() then return empty list")
    public void givenEmptyList_whenRenderAllReservations_thenReturnsEmptyList() throws Exception {
        // Given
        given(reservationService.renderAllReservations()).willReturn(Collections.emptyList());
        List<ReservationModel> expectedModel = new ArrayList<>();

        // When
        MvcResult result = mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andReturn();

        List<ReservationModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // Then
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isEmpty();
        assertThat(actualModels).isEqualTo(expectedModel);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(0);
    }

    @Test
    @DisplayName("Given non-empty list when renderAllReservations() then return list of reservations")
    public void givenNonEmptyList_whenRenderAllReservations_thenReturnReservationList() throws Exception {
        // Given
        Long reservationId1 = 1L;
        Long reservationId2 = 2L;

        List<Reservation> expectedEntities = Arrays.asList(
                new Reservation(reservationId1,
                        LocalDate.of(2024,4, 23), LocalDate.of(2024, 4, 27),
                        new User(), new Apartment(), new Invoice()),
                new Reservation(reservationId2,
                        LocalDate.of(2024,5, 23), LocalDate.of(2024, 5, 27),
                        new User(), new Apartment(), new Invoice())
        );

        given(reservationService.renderAllReservations()).willReturn(expectedEntities);

        // When
        MvcResult result = mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<Reservation> actualEntities = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actualEntities)
                .usingRecursiveComparison()
                .asList()
                .isNotEmpty();
        assertThat(actualEntities)
                .usingRecursiveComparison()
                .isEqualTo(expectedEntities);
        assertThat(actualEntities)
                .usingRecursiveComparison()
                .asList()
                .hasSize(expectedEntities.size());
    }
}
