package hu.webler.weblerapartmentreservation.domain.invoice.model;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceUpdateModel {

    private PaymentType paymentType;
    private Address address;
}
