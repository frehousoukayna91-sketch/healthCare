package com.soukayna.cabinet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Prescription.
 */
@Entity
@Table(name = "prescription")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "prescription_date")
    private LocalDate prescriptionDate;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "followup_date")
    private LocalDate followupDate;

    @Column(name = "notes")
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prescription")
    @JsonIgnoreProperties(value = { "prescription" }, allowSetters = true)
    private Set<PrescriptionItem> prescriptionItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prescription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return this.patientId;
    }

    public Prescription patientId(Integer patientId) {
        this.setPatientId(patientId);
        return this;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDate getPrescriptionDate() {
        return this.prescriptionDate;
    }

    public Prescription prescriptionDate(LocalDate prescriptionDate) {
        this.setPrescriptionDate(prescriptionDate);
        return this;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public Prescription diagnosis(String diagnosis) {
        this.setDiagnosis(diagnosis);
        return this;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getFollowupDate() {
        return this.followupDate;
    }

    public Prescription followupDate(LocalDate followupDate) {
        this.setFollowupDate(followupDate);
        return this;
    }

    public void setFollowupDate(LocalDate followupDate) {
        this.followupDate = followupDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public Prescription notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<PrescriptionItem> getPrescriptionItems() {
        return this.prescriptionItems;
    }

    public void setPrescriptionItems(Set<PrescriptionItem> prescriptionItems) {
        if (this.prescriptionItems != null) {
            this.prescriptionItems.forEach(i -> i.setPrescription(null));
        }
        if (prescriptionItems != null) {
            prescriptionItems.forEach(i -> i.setPrescription(this));
        }
        this.prescriptionItems = prescriptionItems;
    }

    public Prescription prescriptionItems(Set<PrescriptionItem> prescriptionItems) {
        this.setPrescriptionItems(prescriptionItems);
        return this;
    }

    public Prescription addPrescriptionItem(PrescriptionItem prescriptionItem) {
        this.prescriptionItems.add(prescriptionItem);
        prescriptionItem.setPrescription(this);
        return this;
    }

    public Prescription removePrescriptionItem(PrescriptionItem prescriptionItem) {
        this.prescriptionItems.remove(prescriptionItem);
        prescriptionItem.setPrescription(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prescription)) {
            return false;
        }
        return getId() != null && getId().equals(((Prescription) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", prescriptionDate='" + getPrescriptionDate() + "'" +
            ", diagnosis='" + getDiagnosis() + "'" +
            ", followupDate='" + getFollowupDate() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
