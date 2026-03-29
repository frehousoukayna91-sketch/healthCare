package com.soukayna.cabinet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A PrescriptionItem.
 */
@Entity
@Table(name = "prescription_item")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrescriptionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "medication_name")
    private String medicationName;

    @Column(name = "medication_dosage")
    private Integer medicationDosage;

    @Column(name = "instructions")
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "prescriptionItems" }, allowSetters = true)
    private Prescription prescription;

    public Long getId() {
        return this.id;
    }

    public PrescriptionItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public PrescriptionItem duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFrequency() {
        return this.frequency;
    }

    public PrescriptionItem frequency(Integer frequency) {
        this.setFrequency(frequency);
        return this;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getMedicationName() {
        return this.medicationName;
    }

    public PrescriptionItem medicationName(String medicationName) {
        this.setMedicationName(medicationName);
        return this;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Integer getMedicationDosage() {
        return this.medicationDosage;
    }

    public PrescriptionItem medicationDosage(Integer medicationDosage) {
        this.setMedicationDosage(medicationDosage);
        return this;
    }

    public void setMedicationDosage(Integer medicationDosage) {
        this.medicationDosage = medicationDosage;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public PrescriptionItem instructions(String instructions) {
        this.setInstructions(instructions);
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Prescription getPrescription() {
        return this.prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public PrescriptionItem prescription(Prescription prescription) {
        this.setPrescription(prescription);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescriptionItem)) {
            return false;
        }
        return getId() != null && getId().equals(((PrescriptionItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "PrescriptionItem{" +
            "id=" +
            getId() +
            ", duration=" +
            getDuration() +
            ", frequency=" +
            getFrequency() +
            ", medicationName='" +
            getMedicationName() +
            "'" +
            ", medicationDosage=" +
            getMedicationDosage() +
            ", instructions='" +
            getInstructions() +
            "'" +
            "}"
        );
    }
}
