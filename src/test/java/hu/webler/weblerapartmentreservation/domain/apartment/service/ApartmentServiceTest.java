package hu.webler.weblerapartmentreservation.domain.apartment.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.model.ApartmentCreateModel;
import hu.webler.weblerapartmentreservation.domain.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.domain.apartment.persistance.ApartmentRepository;
import hu.webler.weblerapartmentreservation.domain.apartment.util.ApartmentMapper;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Apartment service test")
public class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private ApartmentService apartmentService;

    @Test
    @DisplayName("Given empty apartment list when findAllApartments() then return empty list")
    public void givenEmptyApartmentList_whenFindAllApartments_thenReturnEmptyList() {
        // Given
        when(apartmentRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<ApartmentModel> apartments = apartmentService.findAllApartments();

        // Then
        assertThat(apartments).isEmpty();
    }

    @Test
    @DisplayName("Given non empty apartment list when findAllApartments() then return list of apartment models")
    public void givenNonEmptyApartmentList_whenFindAllApartments_thenReturnListOfApartmentModels() {
        // Given
        List<Apartment> apartmentData = List.of(
                new Apartment(1L, 1, 1, 1, 1, ApartmentType.SINGLE,
                        "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10),
                        new Address(), new ArrayList<>()),
                new Apartment(2L, 2, 2, 2, 2, ApartmentType.SINGLE,
                        "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10),
                        new Address(), new ArrayList<>())
        );
        when(apartmentRepository.findAll()).thenReturn(apartmentData);

        // When
        List<ApartmentModel> apartments = apartmentService.findAllApartments();

        // Then
        assertThat(apartments).hasSize(2);
        assertThat(apartments)
                .usingRecursiveComparison()
                .isEqualTo(apartmentData);
    }

    @Test
    @DisplayName("Given valid apartment id when findApartmentById() then return apartment entity")
    public void givenValidApartmentId_whenFindApartmentById_thenReturnApartmentEntity() {
        // Given
        Long random = new Random().nextLong(1, 4);
        List<Apartment> apartmentData = List.of(
                new Apartment(1L, 1, 1, 1, 1, ApartmentType.SINGLE,
                        "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10),
                        new Address(), new ArrayList<>()),
                new Apartment(2L, 2, 2, 2, 2, ApartmentType.SINGLE,
                        "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10),
                        new Address(), new ArrayList<>()),
                new Apartment(3L, 1, 1, 1, 1, ApartmentType.SINGLE,
                        "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10),
                        new Address(), new ArrayList<>()),
                new Apartment(4L, 2, 2, 2, 2, ApartmentType.SINGLE,
                        "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10),
                        new Address(), new ArrayList<>()));
        Apartment apartment = apartmentData.get(random.intValue() - 1);
        Long searchId = apartment.getId();
        when(apartmentRepository.findById(random)).thenReturn(Optional.ofNullable(apartment));

        // When
        Apartment apartmentResult = apartmentService.findApartmentById(searchId);

        // Then
        assertThat(apartmentResult).isNotNull();
        assertThat(apartmentResult)
                .isEqualTo(apartment);
    }

    @Test
    @DisplayName("Given invalid apartment id when findApartmentById() then throws NoSuchElementException")
    public void givenInvalidApartmentId_whenFindApartmentById_thenThrowsNoSuchElementException() {
        // Given
        Long searchId = new Random().nextLong(1, 100);

        // When / Then
        assertThatThrownBy(() -> apartmentService.findApartmentById(searchId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Apartment with id " + searchId + " was not found");
    }

    @Test
    @DisplayName("Given valid apartmentCreateModel when createApartment() then return apartment model")
    public void givenValidApartmentCreateModel_whenCreateApartment_thenReturnApartmentModel() {
        // Given
        Address address = new Address(1L, "Test data", "Test data", "Test data", "Test data");
        ApartmentCreateModel apartmentCreateModel = new ApartmentCreateModel();
        apartmentCreateModel.setFloorNumber(1);
        apartmentCreateModel.setRoomNumber(1);
        apartmentCreateModel.setMinGuest(1);
        apartmentCreateModel.setMaxGuest(3);
        apartmentCreateModel.setApartmentType(ApartmentType.SINGLE);
        apartmentCreateModel.setDescription("Test data");
        apartmentCreateModel.setPrice(new BigDecimal(10));
        apartmentCreateModel.setAddress(address);

        // Mock
        ApartmentModel expectedModel = new ApartmentModel();
        expectedModel.setId(new Random().nextLong(1, 100));
        expectedModel.setFloorNumber(1);
        expectedModel.setRoomNumber(1);
        expectedModel.setMinGuest(1);
        expectedModel.setMaxGuest(3);
        expectedModel.setApartmentType(ApartmentType.SINGLE);
        expectedModel.setDescription("Test data");
        expectedModel.setPrice(new BigDecimal(10));
        expectedModel.setAddress(address);
        expectedModel.setApartmentStatus(ApartmentStatus.AVAILABLE);
        when(apartmentRepository.save(any())).thenReturn(ApartmentMapper.mapApartmentCreateModelToApartmentEntity(apartmentCreateModel));

        // When
        ApartmentModel createdApartmentModel = apartmentService.createApartment(apartmentCreateModel);

        // Then
        then(expectedModel)
                .usingRecursiveComparison()
                .ignoringFields("id", "apartmentStatus")
                .isEqualTo(createdApartmentModel);
    }

    @Test
    @DisplayName("Given missing address when createApartment() then throws IllegalArgumentException")
    public void givenMissingAddress_whenCreateApartment_thenThrowsIllegalArgumentException() {
        // Given
        ApartmentCreateModel apartmentCreateModel = new ApartmentCreateModel();
        apartmentCreateModel.setFloorNumber(1);
        apartmentCreateModel.setRoomNumber(1);
        apartmentCreateModel.setMinGuest(1);
        apartmentCreateModel.setMaxGuest(3);
        apartmentCreateModel.setApartmentType(ApartmentType.SINGLE);
        apartmentCreateModel.setDescription("Test data");
        apartmentCreateModel.setPrice(new BigDecimal(10));

        // When / Then
        assertThatThrownBy(() -> apartmentService.createApartment(apartmentCreateModel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Address details are required");
    }

    @Test
    @DisplayName("Given missing apartment fields when createApartment() then throws NullPointerException")
    public void givenMissingApartmentFields_whenCreateApartment_thenThrowsNullPointerException() {
        // Given
        Address address = new Address();
        ApartmentCreateModel apartmentCreateModel = new ApartmentCreateModel();
        apartmentCreateModel.setApartmentType(ApartmentType.SINGLE);
        apartmentCreateModel.setPrice(new BigDecimal(10));
        apartmentCreateModel.setAddress(address);

        // When / Then
        assertThatThrownBy(() -> apartmentService.createApartment(apartmentCreateModel))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Given valid apartment id when deleteApartment() then delete apartment")
    public void givenValidInvoiceId_whenDeleteApartment_thenDeleteApartment() {
        // Given
        Long id = new Random().nextLong();
        Apartment apartment = ApartmentMapper.mapApartmentCreateModelToApartmentEntity(
                new ApartmentCreateModel(1, 1, 1, 1, ApartmentType.SINGLE, "Test", new BigDecimal(10), new Address()));
        apartment.setId(id);

        apartmentRepository.save(apartment);
        when(apartmentRepository.findById(id)).thenReturn(Optional.ofNullable(apartment));

        // When
        apartmentService.deleteApartment(id);

        // Then
        verify(apartmentRepository).deleteById(any());
    }
}