package com.soukayna.cabinet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soukayna.cabinet.domain.enumeration.InvoiceStatus;
import com.soukayna.cabinet.domain.enumeration.PaymentMethod;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "appointment_id")
    private String appointmentId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "subtotal", precision = 21, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "tax", precision = 21, scale = 2)
    private BigDecimal tax;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = { "invoice" }, allowSetters = true)
    private Set<InvoiceItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public Invoice invoiceNumber(String invoiceNumber) {
        this.setInvoiceNumber(invoiceNumber);
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getPatientId() {
        return this.patientId;
    }

    public Invoice patientId(Integer patientId) {
        this.setPatientId(patientId);
        return this;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public Invoice patientName(String patientName) {
        this.setPatientName(patientName);
        return this;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentId() {
        return this.appointmentId;
    }

    public Invoice appointmentId(String appointmentId) {
        this.setAppointmentId(appointmentId);
        return this;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Invoice date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public Invoice dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getSubtotal() {
        return this.subtotal;
    }

    public Invoice subtotal(BigDecimal subtotal) {
        this.setSubtotal(subtotal);
        return this;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public Invoice tax(BigDecimal tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public Invoice total(BigDecimal total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public InvoiceStatus getStatus() {
        return this.status;
    }

    public Invoice status(InvoiceStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public Invoice paymentMethod(PaymentMethod paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Set<InvoiceItem> getItems() {
        return this.items;
    }

    public void setItems(Set<InvoiceItem> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setInvoice(null));
        }
        if (items != null) {
            items.forEach(i -> i.setInvoice(this));
        }
        this.items = items;
    }

    public Invoice items(Set<InvoiceItem> items) {
        this.setItems(items);
        return this;
    }

    public Invoice addItem(InvoiceItem item) {
        this.items.add(item);
        item.setInvoice(this);
        return this;
    }

    public Invoice removeItem(InvoiceItem item) {
        this.items.remove(item);
        item.setInvoice(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return getId() != null && getId().equals(((Invoice) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Invoice{" +
            "id=" +
            getId() +
            ", invoiceNumber='" +
            getInvoiceNumber() +
            "'" +
            ", patientId=" +
            getPatientId() +
            ", patientName='" +
            getPatientName() +
            "'" +
            ", appointmentId='" +
            getAppointmentId() +
            "'" +
            ", date='" +
            getDate() +
            "'" +
            ", dueDate='" +
            getDueDate() +
            "'" +
            ", subtotal=" +
            getSubtotal() +
            ", tax=" +
            getTax() +
            ", total=" +
            getTotal() +
            ", status='" +
            getStatus() +
            "'" +
            ", paymentMethod='" +
            getPaymentMethod() +
            "'" +
            "}"
        );
    }
}
