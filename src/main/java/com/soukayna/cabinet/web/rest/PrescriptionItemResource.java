package com.soukayna.cabinet.web.rest;

import com.soukayna.cabinet.domain.PrescriptionItem;
import com.soukayna.cabinet.repository.PrescriptionItemRepository;
import com.soukayna.cabinet.service.PrescriptionItemQueryService;
import com.soukayna.cabinet.service.PrescriptionItemService;
import com.soukayna.cabinet.service.criteria.PrescriptionItemCriteria;
import com.soukayna.cabinet.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.soukayna.cabinet.domain.PrescriptionItem}.
 */
@RestController
@RequestMapping("/api/prescription-items")
public class PrescriptionItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionItemResource.class);

    private static final String ENTITY_NAME = "prescriptionItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrescriptionItemService prescriptionItemService;

    private final PrescriptionItemRepository prescriptionItemRepository;

    private final PrescriptionItemQueryService prescriptionItemQueryService;

    public PrescriptionItemResource(
        PrescriptionItemService prescriptionItemService,
        PrescriptionItemRepository prescriptionItemRepository,
        PrescriptionItemQueryService prescriptionItemQueryService
    ) {
        this.prescriptionItemService = prescriptionItemService;
        this.prescriptionItemRepository = prescriptionItemRepository;
        this.prescriptionItemQueryService = prescriptionItemQueryService;
    }

    /**
     * {@code POST  /prescription-items} : Create a new prescriptionItem.
     *
     * @param prescriptionItem the prescriptionItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prescriptionItem, or with status {@code 400 (Bad Request)} if the prescriptionItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PrescriptionItem> createPrescriptionItem(@RequestBody PrescriptionItem prescriptionItem)
        throws URISyntaxException {
        LOG.debug("REST request to save PrescriptionItem : {}", prescriptionItem);
        if (prescriptionItem.getId() != null) {
            throw new BadRequestAlertException("A new prescriptionItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prescriptionItem = prescriptionItemService.save(prescriptionItem);
        return ResponseEntity.created(new URI("/api/prescription-items/" + prescriptionItem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, prescriptionItem.getId().toString()))
            .body(prescriptionItem);
    }

    /**
     * {@code PUT  /prescription-items/:id} : Updates an existing prescriptionItem.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionItem> updatePrescriptionItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrescriptionItem prescriptionItem
    ) throws URISyntaxException {
        LOG.debug("REST request to update PrescriptionItem : {}, {}", id, prescriptionItem);
        if (prescriptionItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prescriptionItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!prescriptionItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prescriptionItem = prescriptionItemService.update(prescriptionItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescriptionItem.getId().toString()))
            .body(prescriptionItem);
    }

    /**
     * {@code PATCH  /prescription-items/:id} : Partial updates given fields of an existing prescriptionItem.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrescriptionItem> partialUpdatePrescriptionItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrescriptionItem prescriptionItem
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PrescriptionItem partially : {}, {}", id, prescriptionItem);
        if (prescriptionItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prescriptionItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!prescriptionItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrescriptionItem> result = prescriptionItemService.partialUpdate(prescriptionItem);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prescriptionItem.getId().toString())
        );
    }

    /**
     * {@code GET  /prescription-items} : get all the prescriptionItems.
     */
    @GetMapping("")
    public ResponseEntity<List<PrescriptionItem>> getAllPrescriptionItems(
        PrescriptionItemCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get PrescriptionItems by criteria: {}", criteria);
        Page<PrescriptionItem> page = prescriptionItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prescription-items/count} : count all the prescriptionItems.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPrescriptionItems(PrescriptionItemCriteria criteria) {
        LOG.debug("REST request to count PrescriptionItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(prescriptionItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prescription-items/:id} : get the "id" prescriptionItem.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionItem> getPrescriptionItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PrescriptionItem : {}", id);
        Optional<PrescriptionItem> prescriptionItem = prescriptionItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescriptionItem);
    }

    /**
     * {@code DELETE  /prescription-items/:id} : delete the "id" prescriptionItem.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescriptionItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PrescriptionItem : {}", id);
        prescriptionItemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
