package hu.webler.weblerapartmentreservation.domain.invoice.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Invoice service test - unit test")
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    @DisplayName("Given empty invoice list when findAllInvoices() then returns empty list")
    public void givenEmptyInvoiceList_whenGetAllInvoices_thenReturnsEmptyList() {
        // Given
        when(invoiceRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<InvoiceModel> invoices = invoiceService.findAllInvoices();

        // Then
        assertThat(invoices).isEmpty();
    }

    @Test
    @DisplayName("Given non empty invoice list when findAllInvoices() then return list of invoice models")
    public void givenNonEmptyInvoiceList_whenGetAllInvoices_thenReturnsListOfInvoiceModels() {
        // Given
        List<Invoice> invoiceData = List.of(
                new Invoice(1L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(),
                         new Address(), new ArrayList<>()),
                new Invoice(2L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(),
                        new Address(), new ArrayList<>())
        );

        when(invoiceRepository.findAll()).thenReturn(invoiceData);

        // When
        List<InvoiceModel> invoices = invoiceService.findAllInvoices();

        // Then
        assertThat(invoices).hasSize(2);
        assertThat(invoices)
                .usingRecursiveComparison()
                .isEqualTo(invoiceData);
    }
}
