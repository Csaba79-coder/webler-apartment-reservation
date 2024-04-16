package hu.webler.weblerapartmentreservation.domain.invoice.persistence;

import hu.webler.weblerapartmentreservation.domain.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
