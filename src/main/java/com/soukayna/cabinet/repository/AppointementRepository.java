package com.soukayna.cabinet.repository;

import com.soukayna.cabinet.domain.Appointement;
import com.soukayna.cabinet.domain.enumeration.statusAppointement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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

    @Query(
        "select a from Appointement a " +
        "where a.appointementDate = :date " +
        "and (a.status is null or a.status not in :excludedStatuses)"
    )
    List<Appointement> findActiveOnDate(
        @org.springframework.data.repository.query.Param("date") LocalDate date,
        @org.springframework.data.repository.query.Param("excludedStatuses") Collection<statusAppointement> excludedStatuses
    );
}
