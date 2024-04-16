package hu.webler.weblerapartmentreservation.controller;

import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.domain.invoice.model.InvoiceUpdateModel;
import hu.webler.weblerapartmentreservation.domain.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceModel>> renderAllInvoices() {
        return ResponseEntity.status(200).body(invoiceService.findAllInvoices());
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> findInvoiceById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(invoiceService.findInvoiceById(id));
    }

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceModel> createInvoice(@RequestBody InvoiceCreateModel invoiceCreateModel) {
        return ResponseEntity.status(200).body(invoiceService.createInvoice(invoiceCreateModel));
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/invoices/{id}")
    public ResponseEntity<InvoiceModel> updateInvoice(@PathVariable Long id, @RequestBody InvoiceUpdateModel invoiceUpdateModel) {
        return ResponseEntity.status(200).body(invoiceService.updateInvoice(id, invoiceUpdateModel));
    }
}
