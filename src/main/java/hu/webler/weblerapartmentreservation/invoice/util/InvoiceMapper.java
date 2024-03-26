package hu.webler.weblerapartmentreservation.invoice.util;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceUpdateModel;

public class InvoiceMapper {

    public static InvoiceModel mapInvoiceEntityToInvoiceModel(Invoice invoice) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setGenerationDate(invoice.getGenerationDate());
        invoiceModel.setApartment(invoice.getApartment());
        invoiceModel.setUser(invoice.getUser());
        invoiceModel.setAddress(invoice.getAddress());
        invoiceModel.setPaymentDate(invoice.getPaymentDate());
        invoiceModel.setPaymentMethod(invoice.getPaymentMethod());
        return invoiceModel;
    }

    public static Invoice mapInvoiceCreateModelToInvoiceEntity(InvoiceCreateModel invoiceCreateModel) {
        Invoice invoice = new Invoice();
        invoice.setGenerationDate(invoiceCreateModel.getGenerationDate());
        invoice.setApartment(invoiceCreateModel.getApartment());
        invoice.setUser(invoiceCreateModel.getUser());
        invoice.setAddress(invoiceCreateModel.getAddress());
        invoice.setPaymentDate(invoiceCreateModel.getPaymentDate());
        invoice.setPaymentMethod(invoiceCreateModel.getPaymentMethod());
        return invoice;
    }

    public static Invoice mapInvoiceUpdateModelToInvoiceEntity(InvoiceUpdateModel invoiceUpdateModel) {
        Invoice invoice = new Invoice();
        if (invoiceUpdateModel.getGenerationDate() != null) {
            invoice.setGenerationDate(invoiceUpdateModel.getGenerationDate());
        }
        if (invoiceUpdateModel.getApartment() != null) {
            invoice.setApartment(invoiceUpdateModel.getApartment());
        }
        if (invoiceUpdateModel.getUser() != null) {
            invoice.setUser(invoiceUpdateModel.getUser());
        }
        if (invoiceUpdateModel.getAddress() != null) {
            invoice.setAddress(invoiceUpdateModel.getAddress());
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
