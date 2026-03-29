package com.soukayna.cabinet.service.criteria;

import com.soukayna.cabinet.domain.enumeration.InvoiceStatus;
import com.soukayna.cabinet.domain.enumeration.PaymentMethod;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.soukayna.cabinet.domain.Invoice} entity. This class is used
 * in {@link com.soukayna.cabinet.web.rest.InvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&patientName.contains=soukayna}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceCriteria implements Serializable, Criteria {

    /**
     * Class for filtering InvoiceStatus
     */
    public static class InvoiceStatusFilter extends Filter<InvoiceStatus> {

        public InvoiceStatusFilter() {}

        public InvoiceStatusFilter(InvoiceStatusFilter filter) {
            super(filter);
        }

        @Override
        public InvoiceStatusFilter copy() {
            return new InvoiceStatusFilter(this);
        }
    }

    /**
     * Class for filtering PaymentMethod
     */
    public static class PaymentMethodFilter extends Filter<PaymentMethod> {

        public PaymentMethodFilter() {}

        public PaymentMethodFilter(PaymentMethodFilter filter) {
            super(filter);
        }

        @Override
        public PaymentMethodFilter copy() {
            return new PaymentMethodFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter invoiceNumber;

    private IntegerFilter patientId;

    private StringFilter patientName;

    private StringFilter appointmentId;

    private LocalDateFilter date;

    private LocalDateFilter dueDate;

    private BigDecimalFilter subtotal;

    private BigDecimalFilter tax;

    private BigDecimalFilter total;

    private InvoiceStatusFilter status;

    private PaymentMethodFilter paymentMethod;

    private LongFilter itemId;

    private Boolean distinct;

    public InvoiceCriteria() {}

    public InvoiceCriteria(InvoiceCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceNumber = other.optionalInvoiceNumber().map(StringFilter::copy).orElse(null);
        this.patientId = other.optionalPatientId().map(IntegerFilter::copy).orElse(null);
        this.patientName = other.optionalPatientName().map(StringFilter::copy).orElse(null);
        this.appointmentId = other.optionalAppointmentId().map(StringFilter::copy).orElse(null);
        this.date = other.optionalDate().map(LocalDateFilter::copy).orElse(null);
        this.dueDate = other.optionalDueDate().map(LocalDateFilter::copy).orElse(null);
        this.subtotal = other.optionalSubtotal().map(BigDecimalFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(BigDecimalFilter::copy).orElse(null);
        this.total = other.optionalTotal().map(BigDecimalFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(InvoiceStatusFilter::copy).orElse(null);
        this.paymentMethod = other.optionalPaymentMethod().map(PaymentMethodFilter::copy).orElse(null);
        this.itemId = other.optionalItemId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InvoiceCriteria copy() {
        return new InvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public Optional<StringFilter> optionalInvoiceNumber() {
        return Optional.ofNullable(invoiceNumber);
    }

    public StringFilter invoiceNumber() {
        if (invoiceNumber == null) {
            setInvoiceNumber(new StringFilter());
        }
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public IntegerFilter getPatientId() {
        return patientId;
    }

    public Optional<IntegerFilter> optionalPatientId() {
        return Optional.ofNullable(patientId);
    }

    public IntegerFilter patientId() {
        if (patientId == null) {
            setPatientId(new IntegerFilter());
        }
        return patientId;
    }

    public void setPatientId(IntegerFilter patientId) {
        this.patientId = patientId;
    }

    public StringFilter getPatientName() {
        return patientName;
    }

    public Optional<StringFilter> optionalPatientName() {
        return Optional.ofNullable(patientName);
    }

    public StringFilter patientName() {
        if (patientName == null) {
            setPatientName(new StringFilter());
        }
        return patientName;
    }

    public void setPatientName(StringFilter patientName) {
        this.patientName = patientName;
    }

    public StringFilter getAppointmentId() {
        return appointmentId;
    }

    public Optional<StringFilter> optionalAppointmentId() {
        return Optional.ofNullable(appointmentId);
    }

    public StringFilter appointmentId() {
        if (appointmentId == null) {
            setAppointmentId(new StringFilter());
        }
        return appointmentId;
    }

    public void setAppointmentId(StringFilter appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public Optional<LocalDateFilter> optionalDate() {
        return Optional.ofNullable(date);
    }

    public LocalDateFilter date() {
        if (date == null) {
            setDate(new LocalDateFilter());
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public Optional<LocalDateFilter> optionalDueDate() {
        return Optional.ofNullable(dueDate);
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            setDueDate(new LocalDateFilter());
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimalFilter getSubtotal() {
        return subtotal;
    }

    public Optional<BigDecimalFilter> optionalSubtotal() {
        return Optional.ofNullable(subtotal);
    }

    public BigDecimalFilter subtotal() {
        if (subtotal == null) {
            setSubtotal(new BigDecimalFilter());
        }
        return subtotal;
    }

    public void setSubtotal(BigDecimalFilter subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimalFilter getTax() {
        return tax;
    }

    public Optional<BigDecimalFilter> optionalTax() {
        return Optional.ofNullable(tax);
    }

    public BigDecimalFilter tax() {
        if (tax == null) {
            setTax(new BigDecimalFilter());
        }
        return tax;
    }

    public void setTax(BigDecimalFilter tax) {
        this.tax = tax;
    }

    public BigDecimalFilter getTotal() {
        return total;
    }

    public Optional<BigDecimalFilter> optionalTotal() {
        return Optional.ofNullable(total);
    }

    public BigDecimalFilter total() {
        if (total == null) {
            setTotal(new BigDecimalFilter());
        }
        return total;
    }

    public void setTotal(BigDecimalFilter total) {
        this.total = total;
    }

    public InvoiceStatusFilter getStatus() {
        return status;
    }

    public Optional<InvoiceStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public InvoiceStatusFilter status() {
        if (status == null) {
            setStatus(new InvoiceStatusFilter());
        }
        return status;
    }

    public void setStatus(InvoiceStatusFilter status) {
        this.status = status;
    }

    public PaymentMethodFilter getPaymentMethod() {
        return paymentMethod;
    }

    public Optional<PaymentMethodFilter> optionalPaymentMethod() {
        return Optional.ofNullable(paymentMethod);
    }

    public PaymentMethodFilter paymentMethod() {
        if (paymentMethod == null) {
            setPaymentMethod(new PaymentMethodFilter());
        }
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public Optional<LongFilter> optionalItemId() {
        return Optional.ofNullable(itemId);
    }

    public LongFilter itemId() {
        if (itemId == null) {
            setItemId(new LongFilter());
        }
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceCriteria that = (InvoiceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(patientName, that.patientName) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(date, that.date) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(subtotal, that.subtotal) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(total, that.total) &&
            Objects.equals(status, that.status) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            invoiceNumber,
            patientId,
            patientName,
            appointmentId,
            date,
            dueDate,
            subtotal,
            tax,
            total,
            status,
            paymentMethod,
            itemId,
            distinct
        );
    }

    @Override
    public String toString() {
        return (
            "InvoiceCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceNumber().map(f -> "invoiceNumber=" + f + ", ").orElse("") +
            optionalPatientId().map(f -> "patientId=" + f + ", ").orElse("") +
            optionalPatientName().map(f -> "patientName=" + f + ", ").orElse("") +
            optionalAppointmentId().map(f -> "appointmentId=" + f + ", ").orElse("") +
            optionalDate().map(f -> "date=" + f + ", ").orElse("") +
            optionalDueDate().map(f -> "dueDate=" + f + ", ").orElse("") +
            optionalSubtotal().map(f -> "subtotal=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalTotal().map(f -> "total=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalPaymentMethod().map(f -> "paymentMethod=" + f + ", ").orElse("") +
            optionalItemId().map(f -> "itemId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
            "}"
        );
    }
}
