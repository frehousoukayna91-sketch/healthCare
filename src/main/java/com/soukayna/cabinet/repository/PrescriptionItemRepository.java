package com.soukayna.cabinet.repository;

import com.soukayna.cabinet.domain.PrescriptionItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrescriptionItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long>, JpaSpecificationExecutor<PrescriptionItem> {}
