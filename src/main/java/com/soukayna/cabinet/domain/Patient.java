package com.soukayna.cabinet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "date_birth")
    private LocalDate dateBirth;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "notes")
    private String notes;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emeregency_contact_phone")
    private String emeregencyContactPhone;

    @Column(name = "chronicdiseases")
    private String chronicdiseases;

    @Column(name = "medical_history")
    private String medicalHistory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Set<Appointement> appointements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public Patient id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Patient nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Patient lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Patient firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getDateBirth() {
        return this.dateBirth;
    }

    public Patient dateBirth(LocalDate dateBirth) {
        this.setDateBirth(dateBirth);
        return this;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPhone() {
        return this.phone;
    }

    public Patient phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Patient email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public Patient address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public Patient bloodType(String bloodType) {
        this.setBloodType(bloodType);
        return this;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return this.allergies;
    }

    public Patient allergies(String allergies) {
        this.setAllergies(allergies);
        return this;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getNotes() {
        return this.notes;
    }

    public Patient notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getEmergencyContactName() {
        return this.emergencyContactName;
    }

    public Patient emergencyContactName(String emergencyContactName) {
        this.setEmergencyContactName(emergencyContactName);
        return this;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmeregencyContactPhone() {
        return this.emeregencyContactPhone;
    }

    public Patient emeregencyContactPhone(String emeregencyContactPhone) {
        this.setEmeregencyContactPhone(emeregencyContactPhone);
        return this;
    }

    public void setEmeregencyContactPhone(String emeregencyContactPhone) {
        this.emeregencyContactPhone = emeregencyContactPhone;
    }

    public String getChronicdiseases() {
        return this.chronicdiseases;
    }

    public Patient chronicdiseases(String chronicdiseases) {
        this.setChronicdiseases(chronicdiseases);
        return this;
    }

    public void setChronicdiseases(String chronicdiseases) {
        this.chronicdiseases = chronicdiseases;
    }

    public String getMedicalHistory() {
        return this.medicalHistory;
    }

    public Patient medicalHistory(String medicalHistory) {
        this.setMedicalHistory(medicalHistory);
        return this;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Set<Appointement> getAppointements() {
        return this.appointements;
    }

    public void setAppointements(Set<Appointement> appointements) {
        if (this.appointements != null) {
            this.appointements.forEach(i -> i.setPatient(null));
        }
        if (appointements != null) {
            appointements.forEach(i -> i.setPatient(this));
        }
        this.appointements = appointements;
    }

    public Patient appointements(Set<Appointement> appointements) {
        this.setAppointements(appointements);
        return this;
    }

    public Patient addAppointement(Appointement appointement) {
        this.appointements.add(appointement);
        appointement.setPatient(this);
        return this;
    }

    public Patient removeAppointement(Appointement appointement) {
        this.appointements.remove(appointement);
        appointement.setPatient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return getId() != null && getId().equals(((Patient) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", dateBirth='" + getDateBirth() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", bloodType='" + getBloodType() + "'" +
            ", allergies='" + getAllergies() + "'" +
            ", notes='" + getNotes() + "'" +
            ", emergencyContactName='" + getEmergencyContactName() + "'" +
            ", emeregencyContactPhone='" + getEmeregencyContactPhone() + "'" +
            ", chronicdiseases='" + getChronicdiseases() + "'" +
            ", medicalHistory='" + getMedicalHistory() + "'" +
            "}";
    }
}
