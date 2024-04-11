package hu.webler.weblerapartmentreservation.invoice.controller;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceUpdateModel;
import hu.webler.weblerapartmentreservation.invoice.service.InvoiceService;
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

    @PostMapping("/invoices/address/{addressId}")
    public ResponseEntity<InvoiceModel> createInvoice(@RequestBody InvoiceCreateModel invoiceCreateModel,
                                                      @PathVariable(name = "addressId") Long id) {
        return ResponseEntity.status(200).body(invoiceService.createInvoice(invoiceCreateModel, id));
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
