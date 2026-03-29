package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.*; // for static metamodels
import com.soukayna.cabinet.domain.PrescriptionItem;
import com.soukayna.cabinet.repository.PrescriptionItemRepository;
import com.soukayna.cabinet.service.criteria.PrescriptionItemCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PrescriptionItem} entities in the database.
 * The main input is a {@link PrescriptionItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PrescriptionItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrescriptionItemQueryService extends QueryService<PrescriptionItem> {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionItemQueryService.class);

    private final PrescriptionItemRepository prescriptionItemRepository;

    public PrescriptionItemQueryService(PrescriptionItemRepository prescriptionItemRepository) {
        this.prescriptionItemRepository = prescriptionItemRepository;
    }

    /**
     * Return a {@link Page} of {@link PrescriptionItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescriptionItem> findByCriteria(PrescriptionItemCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PrescriptionItem> specification = createSpecification(criteria);
        return prescriptionItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrescriptionItemCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<PrescriptionItem> specification = createSpecification(criteria);
        return prescriptionItemRepository.count(specification);
    }

    /**
     * Function to convert {@link PrescriptionItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PrescriptionItem> createSpecification(PrescriptionItemCriteria criteria) {
        Specification<PrescriptionItem> specification = Specification.where(null);
        if (criteria != null) {
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), PrescriptionItem_.id),
                buildRangeSpecification(criteria.getDuration(), PrescriptionItem_.duration),
                buildRangeSpecification(criteria.getFrequency(), PrescriptionItem_.frequency),
                buildStringSpecification(criteria.getMedicationName(), PrescriptionItem_.medicationName),
                buildRangeSpecification(criteria.getMedicationDosage(), PrescriptionItem_.medicationDosage),
                buildStringSpecification(criteria.getInstructions(), PrescriptionItem_.instructions)
            );
        }
        return specification;
    }
}
