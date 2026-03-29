package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.*; // for static metamodels
import com.soukayna.cabinet.domain.Prescription;
import com.soukayna.cabinet.repository.PrescriptionRepository;
import com.soukayna.cabinet.service.criteria.PrescriptionCriteria;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Prescription} entities in the database.
 * The main input is a {@link PrescriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Prescription} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrescriptionQueryService extends QueryService<Prescription> {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionQueryService.class);

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionQueryService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Return a {@link Page} of {@link Prescription} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Prescription> findByCriteria(PrescriptionCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Prescription> specification = createSpecification(criteria);
        return prescriptionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrescriptionCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Prescription> specification = createSpecification(criteria);
        return prescriptionRepository.count(specification);
    }

    /**
     * Function to convert {@link PrescriptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Prescription> createSpecification(PrescriptionCriteria criteria) {
        Specification<Prescription> specification = Specification.where(null);
        if (criteria != null) {
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Prescription_.id),
                buildRangeSpecification(criteria.getPatientId(), Prescription_.patientId),
                buildRangeSpecification(criteria.getPrescriptionDate(), Prescription_.prescriptionDate),
                buildStringSpecification(criteria.getDiagnosis(), Prescription_.diagnosis),
                buildRangeSpecification(criteria.getFollowupDate(), Prescription_.followupDate),
                buildStringSpecification(criteria.getNotes(), Prescription_.notes),
                buildSpecification(criteria.getPrescriptionItemId(), root ->
                    root.join(Prescription_.prescriptionItems, JoinType.LEFT).get(PrescriptionItem_.id)
                )
            );
        }
        return specification;
    }
}
