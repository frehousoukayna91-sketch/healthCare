package com.soukayna.cabinet.web.rest;

import com.soukayna.cabinet.domain.Prescription;
import com.soukayna.cabinet.domain.PrescriptionItem;
import com.soukayna.cabinet.repository.PatientRepository;
import com.soukayna.cabinet.repository.PrescriptionItemRepository;
import com.soukayna.cabinet.repository.PrescriptionRepository;
import com.soukayna.cabinet.service.PrescriptionQueryService;
import com.soukayna.cabinet.service.PrescriptionService;
import com.soukayna.cabinet.service.criteria.PrescriptionCriteria;
import com.soukayna.cabinet.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.soukayna.cabinet.domain.Prescription}.
 */
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionResource {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionResource.class);

    private static final String ENTITY_NAME = "prescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescriptionService prescriptionService;

    private final PrescriptionRepository prescriptionRepository;

    private final PatientRepository patientRepository;

    private final PrescriptionItemRepository prescriptionItemRepository;

    private final PrescriptionQueryService prescriptionQueryService;

    public PrescriptionResource(
        PrescriptionService prescriptionService,
        PrescriptionRepository prescriptionRepository,
        PatientRepository patientRepository,
        PrescriptionItemRepository prescriptionItemRepository,
        PrescriptionQueryService prescriptionQueryService
    ) {
        this.prescriptionService = prescriptionService;
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.prescriptionItemRepository = prescriptionItemRepository;
        this.prescriptionQueryService = prescriptionQueryService;
    }

    /**
     * {@code POST  /prescriptions} : Create a new prescription.
     *
     * @param prescription the prescription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescription, or with status {@code 400 (Bad Request)} if the prescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) throws URISyntaxException {
        LOG.debug("REST request to save Prescription : {}", prescription);
        if (prescription.getId() != null) {
            throw new BadRequestAlertException("A new prescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        validatePrescriptionRequiredForCreate(prescription);
        prescription = attachAndValidatePrescriptionItems(prescription);
        prescription = prescriptionService.save(prescription);
        return ResponseEntity.created(new URI("/api/prescriptions/" + prescription.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, prescription.getId().toString()))
            .body(prescription);
    }

    /**
     * {@code PUT  /prescriptions/:id} : Updates an existing prescription.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prescription prescription
    ) throws URISyntaxException {
        LOG.debug("REST request to update Prescription : {}, {}", id, prescription);
        if (prescription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prescription.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!prescriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        validatePatientIfProvided(prescription);
        prescription = attachAndValidatePrescriptionItems(prescription);
        prescription = prescriptionService.update(prescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescription.getId().toString()))
            .body(prescription);
    }

    /**
     * {@code PATCH  /prescriptions/:id} : Partial updates given fields of an existing prescription.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Prescription> partialUpdatePrescription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prescription prescription
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Prescription partially : {}, {}", id, prescription);
        if (prescription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prescription.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!prescriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        validatePatientIfProvided(prescription);
        Optional<Prescription> result = prescriptionService.partialUpdate(prescription);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescription.getId().toString())
        );
    }

    /**
     * {@code GET  /prescriptions} : get all the prescriptions.
     */
    @GetMapping("")
    public ResponseEntity<List<Prescription>> getAllPrescriptions(
        PrescriptionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Prescriptions by criteria: {}", criteria);
        Page<Prescription> page = prescriptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescriptions/count} : count all the prescriptions.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPrescriptions(PrescriptionCriteria criteria) {
        LOG.debug("REST request to count Prescriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(prescriptionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prescriptions/:id} : get the "id" prescription.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescription(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Prescription : {}", id);
        Optional<Prescription> prescription = prescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescription);
    }

    /**
     * {@code DELETE  /prescriptions/:id} : delete the "id" prescription.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Prescription : {}", id);
        prescriptionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private void validatePrescriptionRequiredForCreate(Prescription prescription) {
        if (prescription.getPatientId() == null) {
            throw new BadRequestAlertException("Invalid patient id", ENTITY_NAME, "patientidnull");
        }
        if (prescription.getPrescriptionDate() == null) {
            throw new BadRequestAlertException("Date consultation is required", ENTITY_NAME, "dateconsultationnull");
        }
        if (prescription.getDiagnosis() == null || prescription.getDiagnosis().isBlank()) {
            throw new BadRequestAlertException("Diagnosis is required", ENTITY_NAME, "diagnosisnull");
        }
        if (prescription.getPrescriptionItems() == null || prescription.getPrescriptionItems().isEmpty()) {
            throw new BadRequestAlertException("At least one prescription item is required", ENTITY_NAME, "prescriptionitemsrequired");
        }
        validatePatientIfProvided(prescription);
    }

    private void validatePatientIfProvided(Prescription prescription) {
        if (prescription.getPatientId() == null) {
            return;
        }
        if (!patientRepository.existsById(prescription.getPatientId())) {
            throw new BadRequestAlertException("Patient not found", ENTITY_NAME, "patientnotfound");
        }
    }

    private Prescription attachAndValidatePrescriptionItems(Prescription prescription) {
        if (prescription.getPrescriptionItems() == null) {
            return prescription;
        }
        if (prescription.getPrescriptionItems().isEmpty()) {
            return prescription;
        }

        Set<Long> itemIds = prescription.getPrescriptionItems().stream().map(PrescriptionItem::getId).collect(Collectors.toSet());

        if (itemIds.contains(null)) {
            throw new BadRequestAlertException("Each prescription item must have an id", ENTITY_NAME, "prescriptionitemidnull");
        }

        Set<PrescriptionItem> managedItems = prescriptionItemRepository.findAllById(itemIds).stream().collect(Collectors.toSet());
        if (managedItems.size() != itemIds.size()) {
            throw new BadRequestAlertException("One or more prescription items not found", ENTITY_NAME, "prescriptionitemnotfound");
        }

        managedItems.forEach(item -> item.setPrescription(prescription));
        prescription.setPrescriptionItems(managedItems);
        return prescription;
    }
}
