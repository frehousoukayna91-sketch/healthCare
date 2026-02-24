package com.soukayna.cabinet.repository;

import com.soukayna.cabinet.domain.Appointement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Appointement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointementRepository extends JpaRepository<Appointement, Long>, JpaSpecificationExecutor<Appointement> {}
