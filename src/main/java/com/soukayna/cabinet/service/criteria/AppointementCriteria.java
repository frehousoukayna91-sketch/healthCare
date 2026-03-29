package com.soukayna.cabinet.service.criteria;

import com.soukayna.cabinet.domain.enumeration.statusAppointement;
import com.soukayna.cabinet.domain.enumeration.typeAppointement;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.soukayna.cabinet.domain.Appointement} entity. This class is used
 * in {@link com.soukayna.cabinet.web.rest.AppointementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appointements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppointementCriteria implements Serializable, Criteria {

    /**
     * Class for filtering statusAppointement
     */
    public static class statusAppointementFilter extends Filter<statusAppointement> {

        public statusAppointementFilter() {}

        public statusAppointementFilter(statusAppointementFilter filter) {
            super(filter);
        }

        @Override
        public statusAppointementFilter copy() {
            return new statusAppointementFilter(this);
        }
    }

    /**
     * Class for filtering typeAppointement
     */
    public static class typeAppointementFilter extends Filter<typeAppointement> {

        public typeAppointementFilter() {}

        public typeAppointementFilter(typeAppointementFilter filter) {
            super(filter);
        }

        @Override
        public typeAppointementFilter copy() {
            return new typeAppointementFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter duration;

    private statusAppointementFilter status;

    private typeAppointementFilter type;

    private LocalDateFilter appointementDate;

    private StringFilter reasonAppointement;

    private StringFilter notes;

    private IntegerFilter patientId;

    private Boolean distinct;

    public AppointementCriteria() {}

    public AppointementCriteria(AppointementCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.patientId = other.optionalPatientId().map(IntegerFilter::copy).orElse(null);
        this.duration = other.optionalDuration().map(IntegerFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(statusAppointementFilter::copy).orElse(null);
        this.type = other.optionalType().map(typeAppointementFilter::copy).orElse(null);
        this.appointementDate = other.optionalAppointementDate().map(LocalDateFilter::copy).orElse(null);
        this.reasonAppointement = other.optionalReasonAppointement().map(StringFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AppointementCriteria copy() {
        return new AppointementCriteria(this);
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

    public Optional<IntegerFilter> optionalDuration() {
        return Optional.ofNullable(duration);
    }

    public IntegerFilter getDuration() {
        if (duration == null) {
            setDuration(new IntegerFilter());
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public statusAppointementFilter getStatus() {
        return status;
    }

    public Optional<statusAppointementFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public statusAppointementFilter status() {
        if (status == null) {
            setStatus(new statusAppointementFilter());
        }
        return status;
    }

    public void setStatus(statusAppointementFilter status) {
        this.status = status;
    }

    public typeAppointementFilter getType() {
        return type;
    }

    public Optional<typeAppointementFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public typeAppointementFilter type() {
        if (type == null) {
            setType(new typeAppointementFilter());
        }
        return type;
    }

    public void setType(typeAppointementFilter type) {
        this.type = type;
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

    public LocalDateFilter getAppointementDate() {
        return appointementDate;
    }

    public Optional<LocalDateFilter> optionalAppointementDate() {
        return Optional.ofNullable(appointementDate);
    }

    public LocalDateFilter appointementDate() {
        if (appointementDate == null) {
            setAppointementDate(new LocalDateFilter());
        }
        return appointementDate;
    }

    public void setAppointementDate(LocalDateFilter appointementDate) {
        this.appointementDate = appointementDate;
    }

    public StringFilter getReasonAppointement() {
        return reasonAppointement;
    }

    public Optional<StringFilter> optionalReasonAppointement() {
        return Optional.ofNullable(reasonAppointement);
    }

    public StringFilter reasonAppointement() {
        if (reasonAppointement == null) {
            setReasonAppointement(new StringFilter());
        }
        return reasonAppointement;
    }

    public void setReasonAppointement(StringFilter reasonAppointement) {
        this.reasonAppointement = reasonAppointement;
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
        final AppointementCriteria that = (AppointementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(status, that.status) &&
            Objects.equals(type, that.type) &&
            Objects.equals(appointementDate, that.appointementDate) &&
            Objects.equals(reasonAppointement, that.reasonAppointement) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, duration, status, type, appointementDate, reasonAppointement, notes, patientId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppointementCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPatientId().map(f -> "patientId=" + f + ", ").orElse("") +
            optionalDuration().map(f -> "duration=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalAppointementDate().map(f -> "appointementDate=" + f + ", ").orElse("") +
            optionalReasonAppointement().map(f -> "reasonAppointement=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalPatientId().map(f -> "patientId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
