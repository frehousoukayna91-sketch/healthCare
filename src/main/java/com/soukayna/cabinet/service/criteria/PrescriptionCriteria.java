package com.soukayna.cabinet.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.soukayna.cabinet.domain.Prescription} entity. This class is used
 * in {@link com.soukayna.cabinet.web.rest.PrescriptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prescriptions?id.greaterThan=5&diagnosis.contains=viral}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrescriptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter patientId;

    private LocalDateFilter prescriptionDate;

    private StringFilter diagnosis;

    private LocalDateFilter followupDate;

    private StringFilter notes;

    private LongFilter prescriptionItemId;

    private Boolean distinct;

    public PrescriptionCriteria() {}

    public PrescriptionCriteria(PrescriptionCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.patientId = other.optionalPatientId().map(IntegerFilter::copy).orElse(null);
        this.prescriptionDate = other.optionalPrescriptionDate().map(LocalDateFilter::copy).orElse(null);
        this.diagnosis = other.optionalDiagnosis().map(StringFilter::copy).orElse(null);
        this.followupDate = other.optionalFollowupDate().map(LocalDateFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.prescriptionItemId = other.optionalPrescriptionItemId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PrescriptionCriteria copy() {
        return new PrescriptionCriteria(this);
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

    public LocalDateFilter getPrescriptionDate() {
        return prescriptionDate;
    }

    public Optional<LocalDateFilter> optionalPrescriptionDate() {
        return Optional.ofNullable(prescriptionDate);
    }

    public LocalDateFilter prescriptionDate() {
        if (prescriptionDate == null) {
            setPrescriptionDate(new LocalDateFilter());
        }
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDateFilter prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public StringFilter getDiagnosis() {
        return diagnosis;
    }

    public Optional<StringFilter> optionalDiagnosis() {
        return Optional.ofNullable(diagnosis);
    }

    public StringFilter diagnosis() {
        if (diagnosis == null) {
            setDiagnosis(new StringFilter());
        }
        return diagnosis;
    }

    public void setDiagnosis(StringFilter diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDateFilter getFollowupDate() {
        return followupDate;
    }

    public Optional<LocalDateFilter> optionalFollowupDate() {
        return Optional.ofNullable(followupDate);
    }

    public LocalDateFilter followupDate() {
        if (followupDate == null) {
            setFollowupDate(new LocalDateFilter());
        }
        return followupDate;
    }

    public void setFollowupDate(LocalDateFilter followupDate) {
        this.followupDate = followupDate;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public Optional<StringFilter> optionalNotes() {
        return Optional.ofNullable(notes);
    }

    public StringFilter notes() {
        if (notes == null) {
            setNotes(new StringFilter());
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public LongFilter getPrescriptionItemId() {
        return prescriptionItemId;
    }

    public Optional<LongFilter> optionalPrescriptionItemId() {
        return Optional.ofNullable(prescriptionItemId);
    }

    public LongFilter prescriptionItemId() {
        if (prescriptionItemId == null) {
            setPrescriptionItemId(new LongFilter());
        }
        return prescriptionItemId;
    }

    public void setPrescriptionItemId(LongFilter prescriptionItemId) {
        this.prescriptionItemId = prescriptionItemId;
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
        final PrescriptionCriteria that = (PrescriptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(prescriptionDate, that.prescriptionDate) &&
            Objects.equals(diagnosis, that.diagnosis) &&
            Objects.equals(followupDate, that.followupDate) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(prescriptionItemId, that.prescriptionItemId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, prescriptionDate, diagnosis, followupDate, notes, prescriptionItemId, distinct);
    }

    @Override
    public String toString() {
        return (
            "PrescriptionCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPatientId().map(f -> "patientId=" + f + ", ").orElse("") +
            optionalPrescriptionDate().map(f -> "prescriptionDate=" + f + ", ").orElse("") +
            optionalDiagnosis().map(f -> "diagnosis=" + f + ", ").orElse("") +
            optionalFollowupDate().map(f -> "followupDate=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalPrescriptionItemId().map(f -> "prescriptionItemId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
            "}"
        );
    }
}
