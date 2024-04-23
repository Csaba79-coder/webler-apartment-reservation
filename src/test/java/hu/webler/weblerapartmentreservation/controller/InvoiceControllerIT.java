package hu.webler.weblerapartmentreservation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.domain.invoice.service.InvoiceService;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import hu.webler.weblerapartmentreservation.domain.user.model.UserModel;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {InvoiceController.class})
@ExtendWith(MockitoExtension.class)
@DisplayName("Invoice controller test")
public class InvoiceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvoiceService invoiceService;

    private Invoice invoice;
    private InvoiceModel invoiceModel;

    @BeforeEach
    public void init() {
        invoice = new Invoice();
        invoiceModel = new InvoiceModel();
    }

    @Test
    @DisplayName("Given empty list when findAllInvoices then return empty list")
    public void givenEmptyList_whenFindAllInvoices_thenReturnsEmptyList() throws Exception {
        // Given
        given(invoiceService.findAllInvoices()).willReturn(Collections.emptyList());
        List<InvoiceModel> expectedModel = new ArrayList<>();

        // When
        MvcResult result = mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andReturn();

        List<InvoiceModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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
    @DisplayName("Given non-empty list when findAllInvoices then return list")
    public void givenNonEmptyList_whenFindAllInvoices_thenReturnNonEmptyList() throws Exception {
        // Given
        Long invoiceId1 = 1L;
        Long invoiceId2 = 2L;

        List<InvoiceModel> expectedModels = Arrays.asList(
                new InvoiceModel(invoiceId1, LocalDateTime.now(), PaymentType.CARD, new Address()),
                new InvoiceModel(invoiceId2, LocalDateTime.now(), PaymentType.CASH, new Address())
        );

        given(invoiceService.findAllInvoices()).willReturn(expectedModels);

        // When
        MvcResult result = mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        List<InvoiceModel> actualModels = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

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
    @DisplayName("Given valid id when findInvoiceById then return Invoice")
    public void givenValidId_whenFindInvoiceById_thenReturnInvoice() throws Exception {
        // Given
        Long id = new Random().nextLong();
        Address address = new Address(1L, "Test", "Test", "Test", "Test");
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        // Mock
        Invoice invoice = new Invoice(id, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), address, reservations);
        when(invoiceService.findInvoiceById(any(Long.class))).thenReturn(invoice);

        // When
        MvcResult result = mockMvc.perform(get("/api/invoices/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(invoice.getId());

        String responseContent = result.getResponse().getContentAsString();
        InvoiceModel actualInvoice  = objectMapper.readValue(responseContent, InvoiceModel.class);
        assertThat(actualInvoice)
                .usingRecursiveComparison()
                .isEqualTo(invoice);
    }

    @Test
    @DisplayName("Given invalid id when findInvoiceById then throw NoSuchElementException")
    public void givenInvalidId_whenFindInvoiceById_thenThrowNoSuchElementException() throws Exception {
        Long invoiceId = 1L;

        when(mockMvc.perform(get("/api/invoices/{id}", invoiceId)))
                .thenThrow(new NoSuchElementException());
    }

    @Test
    @DisplayName("Given valid id when deleteInvoiceById then delete invoice")
    public void givenValidId_whenDeleteInvoiceById_thenDeleteInvoice() throws Exception {
        // Given
        Long id = new Random().nextLong();
        List<Reservation> reservations = Arrays.asList(
                new Reservation(),
                new Reservation()
        );

        // Mock
        Invoice invoice = new Invoice(id, LocalDateTime.now(),PaymentType.CARD, LocalDate.now(),
                new Address(), reservations);

        MvcResult result = mockMvc.perform(delete("/api/invoices/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Given invalid id when deleteUserById then throw NoSuchElementException")
    public void givenInvalidId_whenDeleteInvoiceById_thenThrowNoSuchElementException() throws Exception {
        // Given
        Long invoiceId = 1L;

        // When / Then
        when(mockMvc.perform(delete("/api/invoices/{id}", invoiceId)))
                .thenThrow(new NoSuchElementException());
    }
}