package com.soukayna.cabinet.repository;

import com.soukayna.cabinet.domain.Invoice;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Invoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
    @EntityGraph(attributePaths = "items")
    Optional<Invoice> findOneWithItemsById(Long id);
}
