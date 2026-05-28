package com.soukayna.cabinet.web.rest;

import com.soukayna.cabinet.domain.Appointement;
import com.soukayna.cabinet.domain.enumeration.statusAppointement;
import com.soukayna.cabinet.repository.AppointementRepository;
import com.soukayna.cabinet.repository.PatientRepository;
import com.soukayna.cabinet.security.AuthoritiesConstants;
import com.soukayna.cabinet.service.AppointementQueryService;
import com.soukayna.cabinet.service.AppointementService;
import com.soukayna.cabinet.service.criteria.AppointementCriteria;
import com.soukayna.cabinet.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.soukayna.cabinet.domain.Appointement}.
 */
@RestController
@RequestMapping("/api/appointements")
@PreAuthorize(
    "hasAnyAuthority(\"" +
    AuthoritiesConstants.MEDECIN +
    "\", \"" +
    AuthoritiesConstants.SECRETAIRE +
    "\", \"" +
    AuthoritiesConstants.ADMIN +
    "\")"
)
public class AppointementResource {

    private static final Logger LOG = LoggerFactory.getLogger(AppointementResource.class);

    private static final String ENTITY_NAME = "appointement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppointementService appointementService;

    private final AppointementRepository appointementRepository;

    private final PatientRepository patientRepository;

    private final AppointementQueryService appointementQueryService;

    public AppointementResource(
        AppointementService appointementService,
        AppointementRepository appointementRepository,
        PatientRepository patientRepository,
        AppointementQueryService appointementQueryService
    ) {
        this.appointementService = appointementService;
        this.appointementRepository = appointementRepository;
        this.patientRepository = patientRepository;
        this.appointementQueryService = appointementQueryService;
    }

    /**
     * {@code POST  /appointements} : Create a new appointement.
     *
     * @param appointement the appointement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appointement, or with status {@code 400 (Bad Request)} if the appointement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Appointement> createAppointement(@RequestBody Appointement appointement) throws URISyntaxException {
        LOG.debug("REST request to save Appointement : {}", appointement);
        if (appointement.getId() != null) {
            throw new BadRequestAlertException("A new appointement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        validatePatientRequiredForCreate(appointement);
        appointement = appointementService.save(appointement);
        return ResponseEntity.created(new URI("/api/appointements/" + appointement.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, appointement.getId().toString()))
            .body(appointement);
    }

    /**
     * {@code PUT  /appointements/:id} : Updates an existing appointement.
     *
     * @param id the id of the appointement to save.
     * @param appointement the appointement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointement,
     * or with status {@code 400 (Bad Request)} if the appointement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appointement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Appointement> updateAppointement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appointement appointement
    ) throws URISyntaxException {
        LOG.debug("REST request to update Appointement : {}, {}", id, appointement);
        if (appointement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        validatePatientIfProvided(appointement);
        appointement = appointementService.update(appointement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appointement.getId().toString()))
            .body(appointement);
    }

    /**
     * {@code PATCH  /appointements/:id} : Partial updates given fields of an existing appointement, field will ignore if it is null
     *
     * @param id the id of the appointement to save.
     * @param appointement the appointement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appointement,
     * or with status {@code 400 (Bad Request)} if the appointement is not valid,
     * or with status {@code 404 (Not Found)} if the appointement is not found,
     * or with status {@code 500 (Internal Server Error)} if the appointement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Appointement> partialUpdateAppointement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Appointement appointement
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Appointement partially : {}, {}", id, appointement);
        if (appointement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appointement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appointementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        validatePatientIfProvided(appointement);
        Optional<Appointement> result = appointementService.partialUpdate(appointement);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appointement.getId().toString())
        );
    }

    /**
     * {@code GET  /appointements} : get all the appointements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appointements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Appointement>> getAllAppointements(
        AppointementCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Appointements by criteria: {}", criteria);

        Page<Appointement> page = appointementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appointements/search/by-status} : search appointements by status.
     *
     * @param status the appointment status.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the matching list in body.
     */
    @GetMapping("/search/by-status")
    public ResponseEntity<List<Appointement>> searchAppointementsByStatus(
        @RequestParam("status") String status,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to search Appointements by status: {}", status);

        String normalizedStatus = status == null ? "" : status.trim();
        if (
            normalizedStatus.length() >= 2 &&
            ((normalizedStatus.startsWith("\"") && normalizedStatus.endsWith("\"")) ||
                (normalizedStatus.startsWith("'") && normalizedStatus.endsWith("'")))
        ) {
            normalizedStatus = normalizedStatus.substring(1, normalizedStatus.length() - 1).trim();
        }
        if (normalizedStatus.isBlank()) {
            throw new BadRequestAlertException("Status is required", ENTITY_NAME, "statusrequired");
        }

        if ("all".equalsIgnoreCase(normalizedStatus)) {
            Page<Appointement> page = appointementRepository.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

        final statusAppointement statusEnum;
        try {
            statusEnum = statusAppointement.valueOf(normalizedStatus.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException("Invalid status value", ENTITY_NAME, "statusinvalid");
        }

        Page<Appointement> page = appointementRepository.findByStatus(statusEnum, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /appointements/count} : count all the appointements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAppointements(AppointementCriteria criteria) {
        LOG.debug("REST request to count Appointements by criteria: {}", criteria);
        return ResponseEntity.ok().body(appointementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /appointements/:id} : get the "id" appointement.
     *
     * @param id the id of the appointement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appointement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointement> getAppointement(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Appointement : {}", id);
        Optional<Appointement> appointement = appointementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appointement);
    }

    /**
     * {@code DELETE  /appointements/:id} : delete the "id" appointement.
     *
     * @param id the id of the appointement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointement(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Appointement : {}", id);
        appointementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private void validatePatientRequiredForCreate(Appointement appointement) {
        if (appointement.getPatient() == null || appointement.getPatient().getId() == null) {
            throw new BadRequestAlertException("Invalid patient id", ENTITY_NAME, "patientidnull");
        }
        validatePatientIfProvided(appointement);
    }

    private void validatePatientIfProvided(Appointement appointement) {
        if (appointement.getPatient() == null) {
            return;
        }
        if (appointement.getPatient().getId() == null) {
            throw new BadRequestAlertException("Invalid patient id", ENTITY_NAME, "patientidnull");
        }
        if (!patientRepository.existsById(appointement.getPatient().getId())) {
            throw new BadRequestAlertException("Patient not found", ENTITY_NAME, "patientnotfound");
        }
    }
}
