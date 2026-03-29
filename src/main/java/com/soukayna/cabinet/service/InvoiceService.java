package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.Invoice;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.soukayna.cabinet.domain.Invoice}.
 */
public interface InvoiceService {
    /**
     * Save a invoice.
     *
     * @param invoice the entity to save.
     * @return the persisted entity.
     */
    Invoice save(Invoice invoice);

    /**
     * Updates a invoice.
     *
     * @param invoice the entity to update.
     * @return the persisted entity.
     */
    Invoice update(Invoice invoice);

    /**
     * Partially updates a invoice.
     *
     * @param invoice the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Invoice> partialUpdate(Invoice invoice);

    /**
     * Get the "id" invoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Invoice> findOne(Long id);

    /**
     * Delete the "id" invoice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
