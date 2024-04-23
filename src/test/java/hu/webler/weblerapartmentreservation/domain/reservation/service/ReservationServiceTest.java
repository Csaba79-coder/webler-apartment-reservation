package hu.webler.weblerapartmentreservation.domain.reservation.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.persistance.ApartmentRepository;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Reservation service test - unit test")
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void init() {
        Address address = new Address(1L, "Country", "postalCode", "city", "line");
        User user = new User(1L, "firstName", "lastName", "Email", "phoneNumber", address, new ArrayList<>());
        Apartment apartment = new Apartment(1L, 1, 1, 1, 3, ApartmentType.SINGLE, "Description",
                ApartmentStatus.AVAILABLE ,new BigDecimal(10L), address, new ArrayList<>());
        Invoice invoice = new Invoice(1L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), address, new ArrayList<>());
    }

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

    @Test
    @DisplayName("Given valid reservation id when findReservationById() then returns reservation entity")
    public void givenValidReservationId_whenFindReservationById_thenReturnsReservationEntity() {
        // Given
        Long random = new Random().nextLong(1, 4);

        List<Reservation> reservationData = List.of(
                new Reservation(1L, LocalDate.now(), LocalDate.now(), new User(), new Apartment(), new Invoice()),
                new Reservation(2L, LocalDate.now(), LocalDate.now(), new User(), new Apartment(), new Invoice()),
                new Reservation(3L, LocalDate.now(), LocalDate.now(), new User(), new Apartment(), new Invoice()),
                new Reservation(4L, LocalDate.now(), LocalDate.now(), new User(), new Apartment(), new Invoice())
        );

        Reservation reservation = reservationData.get(random.intValue() - 1);
        Long searchId = reservation.getId();

        when(reservationRepository.findById(random)).thenReturn(Optional.of(reservation));

        //  When
        Reservation reservationResult = reservationService.renderReservationById(searchId);

        // Then
        assertThat(reservationResult).isNotNull();
        assertThat(reservationResult)
                .isEqualTo(reservation);
    }

    @Test
    @DisplayName("Given invalid reservation id when findReservationById() then throwsNoSuchElementException")
    public void givenInvalidReservationId_whenFindReservationById_thenThrowsNoSuchElementException() {
        // Given
        Long searchId = new Random().nextLong(1, 100);

        // When / Then
        assertThatThrownBy(() -> reservationService.renderReservationById(searchId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Reservation with id " + searchId + " was not found");
    }
}
