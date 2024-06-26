package hu.webler.weblerapartmentreservation.domain.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.webler.weblerapartmentreservation.domain.address.entity.Address;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import hu.webler.weblerapartmentreservation.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime generationDate = LocalDateTime.now();

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice")
    private List<Reservation> reservations;
}
