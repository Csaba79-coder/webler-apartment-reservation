package hu.webler.weblerapartmentreservation.domain.reservation.service;

import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reservation service test - unit test")
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("Given empty reservation list when renderAllReservations() then returns empty list")
    public void givenEmptyReservationList_whenGetAllReservations_thenReturnsEmptyList() {
        // Given
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Reservation> reservations = reservationService.renderAllReservations();

        // Then
        assertThat(reservations).isEmpty();
    }

    @Test
    @DisplayName("Given non empty reservation list when renderAllReservations() then returns list of reservation entites")
    public void givenNonEmptyReservationList_whenRenderAllReservations_thenReturnListOfReservationEntities() {
        // Given
        List<Reservation> reservationData = List.of(
                new Reservation(1L, LocalDate.now(), LocalDate.now(), new User(), new Apartment(), new Invoice()),
                new Reservation(2L, LocalDate.now(), LocalDate.now(), new User(), new Apartment(), new Invoice())
        );

        when(reservationRepository.findAll()).thenReturn(reservationData);

        // When
        List<Reservation> reservations = reservationService.renderAllReservations();

        // Then
        assertThat(reservations).hasSize(2);
        assertThat(reservations)
                .usingRecursiveComparison()
                .isEqualTo(reservationData);
    }

}
