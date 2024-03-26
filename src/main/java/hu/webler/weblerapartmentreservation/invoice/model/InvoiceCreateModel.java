package hu.webler.weblerapartmentreservation.invoice.model;

import hu.webler.weblerapartmentreservation.invoice.value.PaymentDate;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreateModel {

    private LocalDateTime generationDate = LocalDateTime.now();
    private PaymentMethod paymentMethod;
    private PaymentDate paymentDate;
}
