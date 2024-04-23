package hu.webler.weblerapartmentreservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.model.AddressModel;
import hu.webler.weblerapartmentreservation.domain.address.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebMvcTest(AddressController.class)
@ContextConfiguration(classes = {AddressController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Address controller test")
public class AddressControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    @BeforeEach
    public void init() {
        Address address = new Address();
        AddressModel addressModel = new AddressModel();
    }

    @Test
    @DisplayName("Given empty list when findAllAddress then return empty list")
    public void givenEmptyList_whenFindAllAddress_thenReturnsEmptyList() throws Exception {
        // Given
        given(addressService.findAllAddress()).willReturn(Collections.emptyList());
        List<AddressModel> expectedModels = new ArrayList<>();

        // When
        MvcResult result = mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andReturn();

        List<AddressModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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
    @DisplayName("Given non-empty list when findAllAddress then return list")
    public void givenNonEmptyList_whenFindAllAddress_thenReturnNonEmptyList() throws Exception {
        // Given
        Long addressId1 = 1L;
        Long addressId2 = 2L;

        List<AddressModel> expectedModels = Arrays.asList(
                new AddressModel(addressId1, "testCountry1", "testPostalCode1", "testCity1", "testLine1"),
                new AddressModel(addressId2, "testCountry1", "testPostalCode1", "testCity1", "testLine1")
        );

        given(addressService.findAllAddress()).willReturn(expectedModels);

        // When
        MvcResult result = mockMvc.perform(get("/api/address"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<AddressModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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
    @DisplayName("Given valid id when findAddressById then return Address")
    public void givenValidId_whenFindAddressById_thenReturnAddress() throws Exception {
        // Given
        Long id = new Random().nextLong();

        // Mock
        Address address = new Address(id, "testCountry", "testPostalCode", "testCity", "testLine");
        when(addressService.findAddressById(any(Long.class))).thenReturn(address);

        // When
        MvcResult result = mockMvc.perform(get("/api/address/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(address.getId());

        String responseContent = result.getResponse().getContentAsString();
        AddressModel actualAddress = objectMapper.readValue(responseContent, AddressModel.class);

        assertThat(actualAddress)
                .usingRecursiveComparison()
                .isEqualTo(address);
    }

    @Test
    @DisplayName("Given invalid id when findAddressById then throw NoSuchElementException")
    public void givenInvalidId_whenFindAddressById_thenThrowNoSuchElementException() throws Exception {
        Long addressId = 1L;

        when(mockMvc.perform(get("/api/address/{id}", addressId)))
                .thenThrow(new NoSuchElementException());
    }

    @Test
    @DisplayName("Given valid id when deleteAddressById then delete address")
    public void givenValidId_whenDeleteAddressById_thenDeleteAddress() throws Exception {
        // Given
        Long id = new Random().nextLong();

        // Mock
        Address address = new Address(id, "testCountry", "testPostalCode", "testCity", "testLine");

        // When / Then
        MvcResult result = mockMvc.perform(delete("/api/address/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

    }

    @Test
    @DisplayName("Given invalid id when deleteAddressById then throw NoSuchElementException")
    public void givenInvalidId_whenDeleteAddressById_thenThrowNoSuchElementException() throws Exception {
        // Given
        Long addressId = 1L;

        // When / Then
        when(mockMvc.perform(delete("/api/address/{id}", addressId)))
                .thenThrow(new NoSuchElementException());
    }

}