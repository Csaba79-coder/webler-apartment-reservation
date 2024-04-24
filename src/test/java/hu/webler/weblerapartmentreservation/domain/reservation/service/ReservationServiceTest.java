package hu.webler.weblerapartmentreservation.domain.reservation.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.persistance.ApartmentRepository;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationCreateModel;
import hu.webler.weblerapartmentreservation.domain.reservation.model.ReservationModel;
import hu.webler.weblerapartmentreservation.domain.reservation.persistance.ReservationRepository;
import hu.webler.weblerapartmentreservation.domain.reservation.util.ReservationMapper;
import hu.webler.weblerapartmentreservation.domain.user.entity.User;
import hu.webler.weblerapartmentreservation.domain.user.persistance.UserRepository;
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
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private ReservationService reservationService;


    @Test
    @DisplayName("Given empty reservation list when renderAllReservations() then return empty list")
    public void givenEmptyReservationList_whenRenderAllReservations_thenReturnEmptyList() {
        // Given
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Reservation> reservations = reservationService.renderAllReservations();

        // Then
        assertThat(reservations).isEmpty();
    }

    @Test
    @DisplayName("Given non empty reservation list when renderAllReservations() then return list of reservation entities")
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
    @DisplayName("Given valid reservation id when renderReservationById() then return reservation entity")
    public void givenValidReservationId_whenRenderReservationById_thenReturnReservationEntity() {
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
    @DisplayName("Given invalid reservation id when renderReservationById() then throwsNoSuchElementException")
    public void givenInvalidReservationId_whenRenderReservationById_thenThrowsNoSuchElementException() {
        // Given
        Long searchId = new Random().nextLong(1, 100);

        // When / Then
        assertThatThrownBy(() -> reservationService.renderReservationById(searchId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Reservation with id " + searchId + " was not found");
    }

    @Test
    @DisplayName("Given valid reservationCreateModel when createReservation() then return reservation model")
    public void givenValidReservationCreateModel_whenCreateReservation_thenReturnReservationModel() {
        // Given
        Address address = new Address(1L, "country", "postalCode", "city", "line");
        Apartment apartment = new Apartment(1L, 1, 1, 1, 1, ApartmentType.SINGLE, "Test", ApartmentStatus.AVAILABLE,
                new BigDecimal(10), new Address(), new ArrayList<>());
        User user = new User(1L, "FirstName", "lastName", "Email", "phoneNumber", address, new ArrayList<>());
        Invoice invoice = new Invoice(1L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), address, new ArrayList<>());

        ReservationCreateModel reservationCreateModel = new ReservationCreateModel();
        reservationCreateModel.setStartDate(LocalDate.of(2024, 4, 23));
        reservationCreateModel.setEndDate(LocalDate.of(2024,5, 27));
        reservationCreateModel.setApartment(apartment);
        reservationCreateModel.setUser(user);
        reservationCreateModel.setInvoice(invoice);

        // Mock
        ReservationModel expectedModel = new ReservationModel();
        expectedModel.setId(1L);
        expectedModel.setStartDate(LocalDate.of(2024, 4, 23));
        expectedModel.setEndDate(LocalDate.of(2024,5, 27));
        expectedModel.setApartment(apartment);
        expectedModel.setUser(user);
        expectedModel.setInvoice(invoice);
        when(reservationRepository.save(any())).thenReturn(ReservationMapper.mapReservationCreateModelToReservationEntity(reservationCreateModel));

        // When
        ReservationModel createdReservationModel = reservationService.createReservation(reservationCreateModel);

        // Then
        then(expectedModel)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdReservationModel);
    }

    @Test
    @DisplayName("Given missing fields when createReservation() then throws NullPointerException")
    public void givenMissingFields_whenCreateReservation_thenThrowsNullPointerException() {
        // Given
        User user = new User();

        ReservationCreateModel reservationCreateModel = new ReservationCreateModel();
        reservationCreateModel.setUser(user);

        // When / Then
        assertThatThrownBy(() -> reservationService.createReservation(reservationCreateModel))
                .isInstanceOf(NullPointerException.class);
    }
}
