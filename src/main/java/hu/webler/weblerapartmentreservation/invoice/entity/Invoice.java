package hu.webler.weblerapartmentreservation.invoice.entity;

import hu.webler.weblerapartmentreservation.invoice.value.PaymentDate;
import hu.webler.weblerapartmentreservation.invoice.value.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime generationDate;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentDate paymentDate;
}
