package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.Patient;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.soukayna.cabinet.domain.Patient}.
 */
public interface PatientService {
    /**
     * Save a patient.
     *
     * @param patient the entity to save.
     * @return the persisted entity.
     */
    Patient save(Patient patient);

    /**
     * Updates a patient.
     *
     * @param patient the entity to update.
     * @return the persisted entity.
     */
    Patient update(Patient patient);

    /**
     * Partially updates a patient.
     *
     * @param patient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Patient> partialUpdate(Patient patient);

    /**
     * Get the "id" patient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Patient> findOne(Integer id);

    /**
     * Delete the "id" patient.
     *
     * @param id the id of the entity.
     */
    void delete(Integer id);
}
