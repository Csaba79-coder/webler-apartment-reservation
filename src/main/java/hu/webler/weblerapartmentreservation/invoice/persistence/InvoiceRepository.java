package hu.webler.weblerapartmentreservation.invoice.persistence;

import hu.webler.weblerapartmentreservation.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
