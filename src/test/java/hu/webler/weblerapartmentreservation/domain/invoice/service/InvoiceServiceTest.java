package hu.webler.weblerapartmentreservation.domain.invoice.service;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.address.persistence.AddressRepository;
import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.domain.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.domain.invoice.util.InvoiceMapper;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Invoice service test")
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    @DisplayName("Given empty invoice list when findAllInvoices() then return empty list")
    public void givenEmptyInvoiceList_whenFindAllInvoices_thenReturnEmptyList() {
        // Given
        when(invoiceRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<InvoiceModel> invoices = invoiceService.findAllInvoices();

        // Then
        assertThat(invoices).isEmpty();
    }

    @Test
    @DisplayName("Given non empty invoice list when findAllInvoices() then return list of invoice models")
    public void givenNonEmptyInvoiceList_whenFindAllInvoices_thenReturnListOfInvoiceModels() {
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

    @Test
    @DisplayName("Given valid invoice id when findInvoiceById() then return invoice entity")
    public void givenValidInvoiceId_whenFindInvoiceById_thenReturnInvoiceEntity() {
        // Given
        Long random = new Random().nextLong(1, 4);

        List<Invoice> invoiceData = List.of(
                new Invoice(1L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), new Address(), new ArrayList<>()),
                new Invoice(2L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), new Address(), new ArrayList<>()),
                new Invoice(3L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), new Address(), new ArrayList<>()),
                new Invoice(4L, LocalDateTime.now(), PaymentType.CARD, LocalDate.now(), new Address(), new ArrayList<>()));
        Invoice invoice = invoiceData.get(random.intValue() - 1);
        Long searchId = invoice.getId();
        when(invoiceRepository.findById(random)).thenReturn(Optional.ofNullable(invoice));

        // When
        Invoice invoiceResult = invoiceService.findInvoiceById(searchId);

        // Then
        assertThat(invoiceResult).isNotNull();
        assertThat(invoiceResult)
                .isEqualTo(invoice);
    }

    @Test
    @DisplayName("Given invalid invoice id when findInvoiceById() then throws new NoSuchElementException")
    public void givenInvalidInvoiceId_whenFindInvoiceById_thenThrowsNoSuchElementException() {
        // Given
        Long searchId = new Random().nextLong(1, 100);

        // When / Then
        assertThatThrownBy(() -> invoiceService.findInvoiceById(searchId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Invoice with id " + searchId + " was not found");
    }

    @Test
    @DisplayName("Given valid invoiceCreateModel when createInvoice() then return invoice model")
    public void givenValidInvoiceCreateModel_whenCreateInvoice_thenReturnInvoiceModel() {
        // Given
        Address address = new Address(1L, "country", "postalCode", "city", "line");

        InvoiceCreateModel invoiceCreateModel = new InvoiceCreateModel();
        invoiceCreateModel.setPaymentType(PaymentType.CARD);
        invoiceCreateModel.setAddress(address);

        // Mock
        InvoiceModel expectedModel = new InvoiceModel();
        expectedModel.setId(1L);
        expectedModel.setGenerationDate(LocalDateTime.now());
        expectedModel.setPaymentType(PaymentType.CARD);
        expectedModel.setAddress(address);

        when(invoiceRepository.save(any())).thenReturn(InvoiceMapper.mapInvoiceCreateModelToInvoiceEntity(invoiceCreateModel));

        // When
        InvoiceModel createdInvoiceModel = invoiceService.createInvoice(invoiceCreateModel);

        // Then
        then(expectedModel)
                .usingRecursiveComparison()
                .ignoringFields("id", "generationDate")
                .isEqualTo(createdInvoiceModel);
    }

    @Test
    @DisplayName("Given missing address when createInvoice() then throws IllegalArgumentException")
    public void givenMissingAddress_whenCreateInvoice_thenThrowsIllegalArgumentException() {
        // Given
        InvoiceCreateModel invoiceCreateModel = new InvoiceCreateModel();
        invoiceCreateModel.setPaymentType(PaymentType.CARD);

        // When / Then
        assertThatThrownBy(() -> invoiceService.createInvoice(invoiceCreateModel))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Address details are required");
    }

    @Test
    @DisplayName("Given missing invoice fields when createInvoice() then throws NullPointerException")
    public void givenMissingInvoiceFields_whenCreateInvoice_thenThrowsNullPointerException() {
        // Given
        Address address = new Address();

        InvoiceCreateModel invoiceCreateModel = new InvoiceCreateModel();
        invoiceCreateModel.setAddress(address);

        // When / Then
        assertThatThrownBy(() -> invoiceService.createInvoice(invoiceCreateModel))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Given valid invoice id when deleteInvoice() then delete invoice")
    public void givenValidInvoiceId_whenDeleteInvoice_thenDeleteInvoice() {
        // Given
        Long id = new Random().nextLong();
        Invoice invoice = InvoiceMapper.mapInvoiceCreateModelToInvoiceEntity(new InvoiceCreateModel(PaymentType.CARD, new Address()));
        invoice.setId(id);

        invoiceRepository.save(invoice);
        when(invoiceRepository.findById(id)).thenReturn(Optional.ofNullable(invoice));

        // When
        invoiceService.deleteInvoice(id);

        // Then
        verify(invoiceRepository).deleteById(any());
    }
}
