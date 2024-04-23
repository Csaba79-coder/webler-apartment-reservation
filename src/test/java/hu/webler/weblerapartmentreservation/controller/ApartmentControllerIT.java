package hu.webler.weblerapartmentreservation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.apartment.entity.Apartment;
import hu.webler.weblerapartmentreservation.domain.apartment.model.ApartmentModel;
import hu.webler.weblerapartmentreservation.domain.apartment.service.ApartmentService;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentStatus;
import hu.webler.weblerapartmentreservation.domain.apartment.value.ApartmentType;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApartmentController.class)
@ContextConfiguration(classes = {ApartmentController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Apartment controller test")
public class ApartmentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApartmentService apartmentService;

    private Apartment apartment;
    private ApartmentModel apartmentModel;

    @BeforeEach
    public void init() {
        apartment = new Apartment();
        apartmentModel = new ApartmentModel();
    }

    @Test
    @DisplayName("Given empty list when findAllApartments then return empty list")
    public void givenEmptyList_whenFindAllApartments_thenReturnsEmptyList() throws Exception {
        // Given
        given(apartmentService.findAllApartments()).willReturn(Collections.emptyList());
        List<ApartmentModel> expectedModels = new ArrayList<>();

        // When
        MvcResult result = mockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk())
                .andReturn();

        List<ApartmentModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // Then
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isEmpty();
        assertThat(actualModels).isEqualTo(expectedModels);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(0);
    }

    @Test
    @DisplayName("Given non-empty list when findAllApartments then return list")
    public void givenNonEmptyList_whenFindAllApartments_thenReturnNonEmptyList() throws Exception {
        // Given
        Long apartmentId1 = 1L;
        Long apartmentId2 = 2L;

        List<ApartmentModel> expectedModels = Arrays.asList(
                new ApartmentModel(apartmentId1, 1, 1, 1, 1, ApartmentType.SINGLE, "Test value",
                        ApartmentStatus.AVAILABLE, new BigDecimal("1.0") , new Address()),
                new ApartmentModel(apartmentId2, 2, 2, 2, 2, ApartmentType.SINGLE, "Test value 2",
                        ApartmentStatus.AVAILABLE, new BigDecimal("2.0") , new Address())
        );

        given(apartmentService.findAllApartments()).willReturn(expectedModels);

        // When
        MvcResult result = mockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<ApartmentModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .isNotEmpty();
        assertThat(actualModels)
                .usingRecursiveComparison()
                .isEqualTo(expectedModels);
        assertThat(actualModels)
                .usingRecursiveComparison()
                .asList()
                .hasSize(expectedModels.size());
    }

    @Test
    @DisplayName("Given valid id when findApartmentById then return Apartment")
    public void givenValidId_whenFindApartmentById_thenReturnApartment() throws Exception {
        // Given
        Long id = new Random().nextLong();
        Address address = new Address(1L, "test", "test", "test", "test");
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        // Mock
        Apartment apartment = new Apartment(id,1, 1, 1, 1,
                ApartmentType.SINGLE, "test", ApartmentStatus.AVAILABLE, new BigDecimal(1L), address, reservations);
        when(apartmentService.findApartmentById(any(Long.class))).thenReturn(apartment);

        MvcResult result = mockMvc.perform(get("/api/apartments/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(apartment.getId());

        String responseContent = result.getResponse().getContentAsString();
        ApartmentModel actualApartment = objectMapper.readValue(responseContent, ApartmentModel.class);

        assertThat(actualApartment)
                .usingRecursiveComparison()
                .isEqualTo(apartment);
    }

    @Test
    @DisplayName("Given invalid id when findApartmentById then throw NoSuchElementException")
    public void givenInvalidId_whenFindApartmentById_thenThrowNoSuchElementException() throws Exception {
        Long apartmentId = 1L;

        when(mockMvc.perform(get("/api/apartments/{id}", apartmentId)))
                .thenThrow(new NoSuchElementException());
    }

    @Test
    @DisplayName("Given valid id when deleteApartmentById then delete apartment")
    public void givenValidId_whenDeleteApartmentById_thenDeleteApartment() throws Exception {
        // Given
        Long id = new Random().nextLong();
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        // Mock
        Apartment apartment = new Apartment(id, 1, 1, 1, 1, ApartmentType.SINGLE, "Test", ApartmentStatus.AVAILABLE, new BigDecimal(10L),
                new Address(), reservations);

        // When / Then
        MvcResult result = mockMvc.perform(delete("/api/apartments/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Given invalid id when deleteApartmentById then throw NoSuchElementException")
    public void givenInvalidId_whenDeleteAddressById_thenThrowNoSuchElementException() throws Exception {
        // Given
        Long apartmentId = 1L;

        // When / Then
        when(mockMvc.perform(delete("/api/apartments/{id}", apartmentId)))
                .thenThrow(new NoSuchElementException());
    }

}
