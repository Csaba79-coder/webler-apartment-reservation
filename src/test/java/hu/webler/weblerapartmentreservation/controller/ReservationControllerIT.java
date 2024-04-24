package hu.webler.weblerapartmentreservation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.domain.apartment.service.ApartmentService;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.domain.invoice.service.InvoiceService;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
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

    @Test
    @DisplayName("Given valid reservation id when renderReservationById() then return Reservation")
    public void givenValidReservationId_whenRenderReservationById_thenReturnReservation() throws Exception {
        // Given
        Long id = new Random().nextLong(1, 100);
        Address address = new Address(1L, "country", "postalCode", "city", "line");
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        User user = new User(id, "firstName", "lastName", "email", "phoneNumber", address, reservations);
        Invoice invoice = new Invoice(id, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), address, reservations);
        Apartment apartment = new Apartment(id, 1, 1, 1, 1, ApartmentType.SINGLE, "Test value",
                ApartmentStatus.AVAILABLE, new BigDecimal("1.0") , address, reservations);

        // Mock
        Reservation reservation = new Reservation(id, LocalDate.of(2024,4, 23), LocalDate.of(2024, 4, 27),
                user, apartment, invoice);
        when(reservationService.renderReservationById(any(Long.class))).thenReturn(reservation);

        // When
        MvcResult result = mockMvc.perform(get("/api/reservations/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(reservation.getId());
        String responseContent = result.getResponse().getContentAsString();
        ReservationModel actualReservation = objectMapper.readValue(responseContent, ReservationModel.class);

        assertThat(actualReservation)
                .usingRecursiveComparison()
                .ignoringFields("apartment", "user", "invoice")
                .isEqualTo(reservation);
    }
}
