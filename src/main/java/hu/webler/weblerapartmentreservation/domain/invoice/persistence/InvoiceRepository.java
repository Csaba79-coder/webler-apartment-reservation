package hu.webler.weblerapartmentreservation.domain.invoice.persistence;

import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import hu.webler.weblerapartmentreservation.domain.invoice.value.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findInvoiceByGenerationDateAndPaymentTypeAndPaymentDate(LocalDateTime generationDate,
                                                                              PaymentType paymentType, LocalDate paymentDate);
}
