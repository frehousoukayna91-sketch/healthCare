package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.Appointement;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.soukayna.cabinet.domain.Appointement}.
 */
public interface AppointementService {
    /**
     * Save a appointement.
     *
     * @param appointement the entity to save.
     * @return the persisted entity.
     */
    Appointement save(Appointement appointement);

    /**
     * Updates a appointement.
     *
     * @param appointement the entity to update.
     * @return the persisted entity.
     */
    Appointement update(Appointement appointement);

    /**
     * Partially updates a appointement.
     *
     * @param appointement the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Appointement> partialUpdate(Appointement appointement);

    /**
     * Get the "id" appointement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Appointement> findOne(Long id);

    /**
     * Delete the "id" appointement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
