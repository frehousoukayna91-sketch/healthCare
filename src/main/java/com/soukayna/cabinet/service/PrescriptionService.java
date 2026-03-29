package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.Prescription;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.soukayna.cabinet.domain.Prescription}.
 */
public interface PrescriptionService {
    /**
     * Save a prescription.
     *
     * @param prescription the entity to save.
     * @return the persisted entity.
     */
    Prescription save(Prescription prescription);

    /**
     * Updates a prescription.
     *
     * @param prescription the entity to update.
     * @return the persisted entity.
     */
    Prescription update(Prescription prescription);

    /**
     * Partially updates a prescription.
     *
     * @param prescription the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Prescription> partialUpdate(Prescription prescription);

    /**
     * Get the "id" prescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prescription> findOne(Long id);

    /**
     * Delete the "id" prescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
