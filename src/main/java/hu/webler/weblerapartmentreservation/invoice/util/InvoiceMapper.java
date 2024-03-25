package hu.webler.weblerapartmentreservation.invoice.util;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceUpdateModel;

public class InvoiceMapper {

    public static InvoiceModel mapInvoiceEntityToInvoiceModel(Invoice invoice) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setPaymentDate(invoice.getPaymentDate());
        invoiceModel.setPaymentMethod(invoice.getPaymentMethod());
        invoiceModel.setGenerationDate(invoice.getGenerationDate());
        return invoiceModel;
    }

    public static Invoice mapInvoiceCreateModelToInvoiceEntity(InvoiceCreateModel invoiceCreateModel) {
        Invoice invoice = new Invoice();
        invoice.setGenerationDate(invoiceCreateModel.getGenerationDate());
        invoice.setPaymentDate(invoiceCreateModel.getPaymentDate());
        invoice.setPaymentMethod(invoiceCreateModel.getPaymentMethod());
        return invoice;
    }

    public static Invoice mapInvoiceUpdateModelToInvoiceEntity(InvoiceUpdateModel invoiceUpdateModel) {
        Invoice invoice = new Invoice();
        if (invoiceUpdateModel.getGenerationDate() != null) {
            invoice.setGenerationDate(invoiceUpdateModel.getGenerationDate());
        }
        if (invoiceUpdateModel.getPaymentDate() != null) {
            invoice.setPaymentDate(invoiceUpdateModel.getPaymentDate());
        }
        if (invoiceUpdateModel.getPaymentMethod() != null) {
            invoice.setPaymentMethod(invoiceUpdateModel.getPaymentMethod());
        }
        return invoice;
    }

    private InvoiceMapper() {
    }
}
