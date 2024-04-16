package hu.webler.weblerapartmentreservation.domain.invoice.model;

import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {

    private Long id;
    private LocalDateTime generationDate;
    private PaymentType paymentType;
    private Address address;
}
