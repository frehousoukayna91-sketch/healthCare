package com.soukayna.cabinet.web.rest;

import com.soukayna.cabinet.domain.Invoice;
import com.soukayna.cabinet.repository.InvoiceRepository;
import com.soukayna.cabinet.security.AuthoritiesConstants;
import com.soukayna.cabinet.service.InvoiceQueryService;
import com.soukayna.cabinet.service.InvoiceService;
import com.soukayna.cabinet.service.criteria.InvoiceCriteria;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.soukayna.cabinet.domain.Invoice}.
 */
@RestController
@RequestMapping("/api/invoices")
@PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SECRETAIRE + "\", \"" + AuthoritiesConstants.ADMIN + "\")")
public class InvoiceResource {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceResource.class);

    private static final String ENTITY_NAME = "invoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceService invoiceService;

    private final InvoiceRepository invoiceRepository;

    private final InvoiceQueryService invoiceQueryService;

    public InvoiceResource(InvoiceService invoiceService, InvoiceRepository invoiceRepository, InvoiceQueryService invoiceQueryService) {
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.invoiceQueryService = invoiceQueryService;
    }

    /**
     * {@code POST  /invoices} : Create a new invoice.
     */
    @PostMapping("")
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) throws URISyntaxException {
        LOG.debug("REST request to save Invoice : {}", invoice);
        if (invoice.getId() != null) {
            throw new BadRequestAlertException("A new invoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        validateRequiredFields(invoice);
        invoice = invoiceService.save(invoice);
        return ResponseEntity.created(new URI("/api/invoices/" + invoice.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, invoice.getId().toString()))
            .body(invoice);
    }

    /**
     * {@code PUT  /invoices/:id} : Updates an existing invoice.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable(value = "id", required = false) final Long id, @RequestBody Invoice invoice)
        throws URISyntaxException {
        LOG.debug("REST request to update Invoice : {}, {}", id, invoice);
        if (invoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!invoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        validateRequiredFields(invoice);
        invoice = invoiceService.update(invoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoice.getId().toString()))
            .body(invoice);
    }

    /**
     * {@code PATCH  /invoices/:id} : Partial updates given fields of an existing invoice.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Invoice> partialUpdateInvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Invoice invoice
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Invoice partially : {}, {}", id, invoice);
        if (invoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!invoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Invoice> result = invoiceService.partialUpdate(invoice);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoice.getId().toString())
        );
    }

    /**
     * {@code GET  /invoices} : get all the invoices.
     */
    @GetMapping("")
    public ResponseEntity<List<Invoice>> getAllInvoices(
        InvoiceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Invoices by criteria: {}", criteria);
        Page<Invoice> page = invoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoices/count} : count all the invoices.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInvoices(InvoiceCriteria criteria) {
        LOG.debug("REST request to count Invoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoices/:id} : get the "id" invoice.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Invoice : {}", id);
        Optional<Invoice> invoice = invoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoice);
    }

    /**
     * {@code DELETE  /invoices/:id} : delete the "id" invoice.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Invoice : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    private void validateRequiredFields(Invoice invoice) {
        if (invoice.getPatientName() == null || invoice.getPatientName().isBlank()) {
            throw new BadRequestAlertException("patient_name is required", ENTITY_NAME, "patientnamenull");
        }
        if (invoice.getDate() == null) {
            throw new BadRequestAlertException("date is required", ENTITY_NAME, "datenull");
        }
    }
}
