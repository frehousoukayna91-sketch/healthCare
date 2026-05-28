package com.soukayna.cabinet.service.impl;

import com.soukayna.cabinet.domain.Invoice;
import com.soukayna.cabinet.repository.InvoiceRepository;
import com.soukayna.cabinet.service.InvoiceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.soukayna.cabinet.domain.Invoice}.
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        LOG.debug("Request to save Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        LOG.debug("Request to update Invoice : {}", invoice);
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> partialUpdate(Invoice invoice) {
        LOG.debug("Request to partially update Invoice : {}", invoice);

        return invoiceRepository
            .findById(invoice.getId())
            .map(existingInvoice -> {
                if (invoice.getInvoiceNumber() != null) {
                    existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
                }
                if (invoice.getPatientId() != null) {
                    existingInvoice.setPatientId(invoice.getPatientId());
                }
                if (invoice.getPatientName() != null) {
                    existingInvoice.setPatientName(invoice.getPatientName());
                }
                if (invoice.getAppointmentId() != null) {
                    existingInvoice.setAppointmentId(invoice.getAppointmentId());
                }
                if (invoice.getDate() != null) {
                    existingInvoice.setDate(invoice.getDate());
                }
                if (invoice.getDueDate() != null) {
                    existingInvoice.setDueDate(invoice.getDueDate());
                }
                if (invoice.getSubtotal() != null) {
                    existingInvoice.setSubtotal(invoice.getSubtotal());
                }
                if (invoice.getTax() != null) {
                    existingInvoice.setTax(invoice.getTax());
                }
                if (invoice.getTotal() != null) {
                    existingInvoice.setTotal(invoice.getTotal());
                }
                if (invoice.getStatus() != null) {
                    existingInvoice.setStatus(invoice.getStatus());
                }
                if (invoice.getPaymentMethod() != null) {
                    existingInvoice.setPaymentMethod(invoice.getPaymentMethod());
                }
                if (invoice.getItems() != null && !invoice.getItems().isEmpty()) {
                    existingInvoice.setItems(invoice.getItems());
                }

                return existingInvoice;
            })
            .map(invoiceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findOne(Long id) {
        LOG.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findOneWithItemsById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
