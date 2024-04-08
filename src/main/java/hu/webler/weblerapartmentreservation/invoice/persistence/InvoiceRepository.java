package hu.webler.weblerapartmentreservation.invoice.persistence;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findInvoiceById(Long id);
}
