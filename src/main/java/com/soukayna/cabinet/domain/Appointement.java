package com.soukayna.cabinet.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soukayna.cabinet.domain.enumeration.statusAppointement;
import com.soukayna.cabinet.domain.enumeration.typeAppointement;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A Appointement.
 */
@Entity
@Table(name = "appointement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Appointement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /* 
    @Column(name = "patient_id")
    private Integer patientId; */

    @Column(name = "duration")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private statusAppointement status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private typeAppointement type;

    @Column(name = "appointement_date")
    private LocalDate appointementDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(name = "appointement_time")
    private LocalTime appointementTime;

    @Column(name = "reason_appointement")
    private String reasonAppointement;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "appointements" }, allowSetters = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appointement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*     public Integer getPatientId() {
        return this.patientId;
    } */

    /*     public Appointement patientId(Integer patientId) {
        this.setPatientId(patientId);
        return this;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    } */

    public Integer getDuration() {
        return this.duration;
    }

    public Appointement duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public statusAppointement getStatus() {
        return this.status;
    }

    public Appointement status(statusAppointement status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(statusAppointement status) {
        this.status = status;
    }

    public typeAppointement getType() {
        return this.type;
    }

    public Appointement type(typeAppointement type) {
        this.setType(type);
        return this;
    }

    public void setType(typeAppointement type) {
        this.type = type;
    }

    public LocalDate getAppointementDate() {
        return this.appointementDate;
    }

    public Appointement appointementDate(LocalDate appointementDate) {
        this.setAppointementDate(appointementDate);
        return this;
    }

    public void setAppointementDate(LocalDate appointementDate) {
        this.appointementDate = appointementDate;
    }

    public LocalTime getAppointementTime() {
        return this.appointementTime;
    }

    public Appointement appointementTime(LocalTime appointementTime) {
        this.setAppointementTime(appointementTime);
        return this;
    }

    public void setAppointementTime(LocalTime appointementTime) {
        this.appointementTime = appointementTime;
    }

    public String getReasonAppointement() {
        return this.reasonAppointement;
    }

    public Appointement reasonAppointement(String reasonAppointement) {
        this.setReasonAppointement(reasonAppointement);
        return this;
    }

    public void setReasonAppointement(String reasonAppointement) {
        this.reasonAppointement = reasonAppointement;
    }

    public String getNotes() {
        return this.notes;
    }

    public Appointement notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointement patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointement)) {
            return false;
        }
        return getId() != null && getId().equals(((Appointement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appointement{" +
            "id=" + getId() +
            ", duration=" + getDuration() +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            ", appointementDate='" + getAppointementDate() + "'" +
            ", appointementTime='" + getAppointementTime() + "'" +
            ", reasonAppointement='" + getReasonAppointement() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
