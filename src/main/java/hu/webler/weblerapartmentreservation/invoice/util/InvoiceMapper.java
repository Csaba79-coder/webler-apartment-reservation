package hu.webler.weblerapartmentreservation.invoice.util;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceCreateModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceModel;
import hu.webler.weblerapartmentreservation.invoice.model.InvoiceUpdateModel;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentType;

import java.time.LocalDate;

public class InvoiceMapper {

    public static InvoiceModel mapInvoiceEntityToInvoiceModel(Invoice invoice) {
        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setId(invoice.getId());
        invoiceModel.setGenerationDate(invoice.getGenerationDate());
        invoiceModel.setAddress(invoice.getAddress());
        invoiceModel.setPaymentType(invoice.getPaymentType());
        return invoiceModel;
    }

    public static Invoice mapInvoiceCreateModelToInvoiceEntity(InvoiceCreateModel model) {
        Invoice invoice = new Invoice();
        if (model.getPaymentType() == PaymentType.CASH || model.getPaymentType() == PaymentType.CARD) {
            invoice.setPaymentDate(LocalDate.now());
        } else if (model.getPaymentType() == PaymentType.TRANSFER) {
            invoice.setPaymentDate(LocalDate.now().plusDays(8));
        } else {
            // TODO implement logic to set the invoice date of arrival! :)
            invoice.setPaymentDate(LocalDate.now().plusDays(100)); // instead of this! just for test now 100 days
        }
        invoice.setPaymentType(model.getPaymentType());
        invoice.setAddress(model.getAddress());
        return invoice;
    }

    public static Invoice mapInvoiceUpdateModelToInvoiceEntity(InvoiceUpdateModel invoiceUpdateModel) {
        Invoice invoice = new Invoice();
        if (invoiceUpdateModel.getAddress() != null) {
            invoice.setAddress(invoiceUpdateModel.getAddress());
        }
        if (invoiceUpdateModel.getPaymentType() != null) {
            invoice.setPaymentType(invoiceUpdateModel.getPaymentType());
        }
        return invoice;
    }

    private InvoiceMapper() {
    }
}
