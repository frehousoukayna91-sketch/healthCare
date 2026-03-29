package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.PrescriptionItem;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.soukayna.cabinet.domain.PrescriptionItem}.
 */
public interface PrescriptionItemService {
    /**
     * Save a prescriptionItem.
     *
     * @param prescriptionItem the entity to save.
     * @return the persisted entity.
     */
    PrescriptionItem save(PrescriptionItem prescriptionItem);

    /**
     * Updates a prescriptionItem.
     *
     * @param prescriptionItem the entity to update.
     * @return the persisted entity.
     */
    PrescriptionItem update(PrescriptionItem prescriptionItem);

    /**
     * Partially updates a prescriptionItem.
     *
     * @param prescriptionItem the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrescriptionItem> partialUpdate(PrescriptionItem prescriptionItem);

    /**
     * Get the "id" prescriptionItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrescriptionItem> findOne(Long id);

    /**
     * Delete the "id" prescriptionItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
