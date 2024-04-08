package hu.webler.weblerapartmentreservation.invoice.service;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceUpdateModel;
import hu.webler.weblerapartmentreservation.invoice.persistence.InvoiceRepository;
import hu.webler.weblerapartmentreservation.invoice.util.InvoiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<InvoiceModel> findAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceMapper::mapInvoiceEntityToInvoiceModel)
                .collect(Collectors.toList());
    }

    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> {
                    String message = String.format("Invoice with id %d was not found", id);
                    log.info(message);
                    return new RuntimeException(message);
                });
    }

    public InvoiceModel createInvoice(InvoiceCreateModel invoiceCreateModel) {
        return InvoiceMapper.mapInvoiceEntityToInvoiceModel(invoiceRepository
                .save(InvoiceMapper.mapInvoiceCreateModelToInvoiceEntity(invoiceCreateModel)));
    }

    public void deleteInvoice(Long id) {
        findInvoiceById(id);
        invoiceRepository.deleteById(id);
    }

    public InvoiceModel updateInvoice(Long id, InvoiceUpdateModel invoiceUpdateModel) {
        Invoice invoice = findInvoiceById(id);
        if (invoice != null) {
            return InvoiceMapper.mapInvoiceEntityToInvoiceModel(invoiceRepository
                    .save(InvoiceMapper.mapInvoiceUpdateModelToInvoiceEntity(invoiceUpdateModel)));
        }
        return null;
    }
}
