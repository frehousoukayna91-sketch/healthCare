package com.soukayna.cabinet.service;

import com.soukayna.cabinet.domain.*; // for static metamodels
import com.soukayna.cabinet.domain.Patient;
import com.soukayna.cabinet.repository.PatientRepository;
import com.soukayna.cabinet.service.criteria.PatientCriteria;
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
 * Service for executing complex queries for {@link Patient} entities in the database.
 * The main input is a {@link PatientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Patient} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientQueryService extends QueryService<Patient> {

    private static final Logger LOG = LoggerFactory.getLogger(PatientQueryService.class);

    private final PatientRepository patientRepository;

    public PatientQueryService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Return a {@link Page} of {@link Patient} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Patient> findByCriteria(PatientCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Patient> createSpecification(PatientCriteria criteria) {
        Specification<Patient> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Patient_.id),
                buildStringSpecification(criteria.getNom(), Patient_.nom),
                buildStringSpecification(criteria.getLastName(), Patient_.lastName),
                buildStringSpecification(criteria.getFirstName(), Patient_.firstName),
                buildRangeSpecification(criteria.getDateBirth(), Patient_.dateBirth),
                buildStringSpecification(criteria.getPhone(), Patient_.phone),
                buildStringSpecification(criteria.getEmail(), Patient_.email),
                buildStringSpecification(criteria.getAddress(), Patient_.address),
                buildStringSpecification(criteria.getBloodType(), Patient_.bloodType),
                buildStringSpecification(criteria.getAllergies(), Patient_.allergies),
                buildStringSpecification(criteria.getNotes(), Patient_.notes),
                buildStringSpecification(criteria.getEmergencyContactName(), Patient_.emergencyContactName),
                buildStringSpecification(criteria.getEmeregencyContactPhone(), Patient_.emeregencyContactPhone),
                buildStringSpecification(criteria.getChronicdiseases(), Patient_.chronicdiseases),
                buildStringSpecification(criteria.getMedicalHistory(), Patient_.medicalHistory),
                buildSpecification(criteria.getAppointementId(), root ->
                    root.join(Patient_.appointements, JoinType.LEFT).get(Appointement_.id)
                )
            );
        }
        return specification;
    }
}
