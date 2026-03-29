package com.soukayna.cabinet.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.soukayna.cabinet.domain.PrescriptionItem} entity. This class is used
 * in {@link com.soukayna.cabinet.web.rest.PrescriptionItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prescription-items?id.greaterThan=5&medicationName.contains=paracetamol}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrescriptionItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter duration;

    private IntegerFilter frequency;

    private StringFilter medicationName;

    private IntegerFilter medicationDosage;

    private StringFilter instructions;

    private Boolean distinct;

    public PrescriptionItemCriteria() {}

    public PrescriptionItemCriteria(PrescriptionItemCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.duration = other.optionalDuration().map(IntegerFilter::copy).orElse(null);
        this.frequency = other.optionalFrequency().map(IntegerFilter::copy).orElse(null);
        this.medicationName = other.optionalMedicationName().map(StringFilter::copy).orElse(null);
        this.medicationDosage = other.optionalMedicationDosage().map(IntegerFilter::copy).orElse(null);
        this.instructions = other.optionalInstructions().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PrescriptionItemCriteria copy() {
        return new PrescriptionItemCriteria(this);
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

    public IntegerFilter getDuration() {
        return duration;
    }

    public Optional<IntegerFilter> optionalDuration() {
        return Optional.ofNullable(duration);
    }

    public IntegerFilter duration() {
        if (duration == null) {
            setDuration(new IntegerFilter());
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public IntegerFilter getFrequency() {
        return frequency;
    }

    public Optional<IntegerFilter> optionalFrequency() {
        return Optional.ofNullable(frequency);
    }

    public IntegerFilter frequency() {
        if (frequency == null) {
            setFrequency(new IntegerFilter());
        }
        return frequency;
    }

    public void setFrequency(IntegerFilter frequency) {
        this.frequency = frequency;
    }

    public StringFilter getMedicationName() {
        return medicationName;
    }

    public Optional<StringFilter> optionalMedicationName() {
        return Optional.ofNullable(medicationName);
    }

    public StringFilter medicationName() {
        if (medicationName == null) {
            setMedicationName(new StringFilter());
        }
        return medicationName;
    }

    public void setMedicationName(StringFilter medicationName) {
        this.medicationName = medicationName;
    }

    public IntegerFilter getMedicationDosage() {
        return medicationDosage;
    }

    public Optional<IntegerFilter> optionalMedicationDosage() {
        return Optional.ofNullable(medicationDosage);
    }

    public IntegerFilter medicationDosage() {
        if (medicationDosage == null) {
            setMedicationDosage(new IntegerFilter());
        }
        return medicationDosage;
    }

    public void setMedicationDosage(IntegerFilter medicationDosage) {
        this.medicationDosage = medicationDosage;
    }

    public StringFilter getInstructions() {
        return instructions;
    }

    public Optional<StringFilter> optionalInstructions() {
        return Optional.ofNullable(instructions);
    }

    public StringFilter instructions() {
        if (instructions == null) {
            setInstructions(new StringFilter());
        }
        return instructions;
    }

    public void setInstructions(StringFilter instructions) {
        this.instructions = instructions;
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
        final PrescriptionItemCriteria that = (PrescriptionItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(medicationName, that.medicationName) &&
            Objects.equals(medicationDosage, that.medicationDosage) &&
            Objects.equals(instructions, that.instructions) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, frequency, medicationName, medicationDosage, instructions, distinct);
    }

    @Override
    public String toString() {
        return (
            "PrescriptionItemCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDuration().map(f -> "duration=" + f + ", ").orElse("") +
            optionalFrequency().map(f -> "frequency=" + f + ", ").orElse("") +
            optionalMedicationName().map(f -> "medicationName=" + f + ", ").orElse("") +
            optionalMedicationDosage().map(f -> "medicationDosage=" + f + ", ").orElse("") +
            optionalInstructions().map(f -> "instructions=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
            "}"
        );
    }
}
