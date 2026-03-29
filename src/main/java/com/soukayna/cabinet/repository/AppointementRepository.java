package com.soukayna.cabinet.repository;

import com.soukayna.cabinet.domain.Appointement;
import com.soukayna.cabinet.domain.enumeration.statusAppointement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Appointement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointementRepository extends JpaRepository<Appointement, Long>, JpaSpecificationExecutor<Appointement> {
    Page<Appointement> findByStatus(statusAppointement status, Pageable pageable);
}
