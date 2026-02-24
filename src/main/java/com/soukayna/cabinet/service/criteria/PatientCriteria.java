package com.soukayna.cabinet.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.soukayna.cabinet.domain.Patient} entity. This class is used
 * in {@link com.soukayna.cabinet.web.rest.PatientResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patients?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PatientCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter id;

    private StringFilter nom;

    private StringFilter lastName;

    private StringFilter firstName;

    private LocalDateFilter dateBirth;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter address;

    private StringFilter bloodType;

    private StringFilter allergies;

    private StringFilter notes;

    private StringFilter emergencyContactName;

    private StringFilter emeregencyContactPhone;

    private StringFilter chronicdiseases;

    private StringFilter medicalHistory;

    private LongFilter appointementId;

    private Boolean distinct;

    public PatientCriteria() {}

    public PatientCriteria(PatientCriteria other) {
        this.id = other.optionalId().map(IntegerFilter::copy).orElse(null);
        this.nom = other.optionalNom().map(StringFilter::copy).orElse(null);
        this.lastName = other.optionalLastName().map(StringFilter::copy).orElse(null);
        this.firstName = other.optionalFirstName().map(StringFilter::copy).orElse(null);
        this.dateBirth = other.optionalDateBirth().map(LocalDateFilter::copy).orElse(null);
        this.phone = other.optionalPhone().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.address = other.optionalAddress().map(StringFilter::copy).orElse(null);
        this.bloodType = other.optionalBloodType().map(StringFilter::copy).orElse(null);
        this.allergies = other.optionalAllergies().map(StringFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.emergencyContactName = other.optionalEmergencyContactName().map(StringFilter::copy).orElse(null);
        this.emeregencyContactPhone = other.optionalEmeregencyContactPhone().map(StringFilter::copy).orElse(null);
        this.chronicdiseases = other.optionalChronicdiseases().map(StringFilter::copy).orElse(null);
        this.medicalHistory = other.optionalMedicalHistory().map(StringFilter::copy).orElse(null);
        this.appointementId = other.optionalAppointementId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PatientCriteria copy() {
        return new PatientCriteria(this);
    }

    public IntegerFilter getId() {
        return id;
    }

    public Optional<IntegerFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public IntegerFilter id() {
        if (id == null) {
            setId(new IntegerFilter());
        }
        return id;
    }

    public void setId(IntegerFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public Optional<StringFilter> optionalNom() {
        return Optional.ofNullable(nom);
    }

    public StringFilter nom() {
        if (nom == null) {
            setNom(new StringFilter());
        }
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public Optional<StringFilter> optionalLastName() {
        return Optional.ofNullable(lastName);
    }

    public StringFilter lastName() {
        if (lastName == null) {
            setLastName(new StringFilter());
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public Optional<StringFilter> optionalFirstName() {
        return Optional.ofNullable(firstName);
    }

    public StringFilter firstName() {
        if (firstName == null) {
            setFirstName(new StringFilter());
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public LocalDateFilter getDateBirth() {
        return dateBirth;
    }

    public Optional<LocalDateFilter> optionalDateBirth() {
        return Optional.ofNullable(dateBirth);
    }

    public LocalDateFilter dateBirth() {
        if (dateBirth == null) {
            setDateBirth(new LocalDateFilter());
        }
        return dateBirth;
    }

    public void setDateBirth(LocalDateFilter dateBirth) {
        this.dateBirth = dateBirth;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public Optional<StringFilter> optionalPhone() {
        return Optional.ofNullable(phone);
    }

    public StringFilter phone() {
        if (phone == null) {
            setPhone(new StringFilter());
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getAddress() {
        return address;
    }

    public Optional<StringFilter> optionalAddress() {
        return Optional.ofNullable(address);
    }

    public StringFilter address() {
        if (address == null) {
            setAddress(new StringFilter());
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getBloodType() {
        return bloodType;
    }

    public Optional<StringFilter> optionalBloodType() {
        return Optional.ofNullable(bloodType);
    }

    public StringFilter bloodType() {
        if (bloodType == null) {
            setBloodType(new StringFilter());
        }
        return bloodType;
    }

    public void setBloodType(StringFilter bloodType) {
        this.bloodType = bloodType;
    }

    public StringFilter getAllergies() {
        return allergies;
    }

    public Optional<StringFilter> optionalAllergies() {
        return Optional.ofNullable(allergies);
    }

    public StringFilter allergies() {
        if (allergies == null) {
            setAllergies(new StringFilter());
        }
        return allergies;
    }

    public void setAllergies(StringFilter allergies) {
        this.allergies = allergies;
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

    public StringFilter getEmergencyContactName() {
        return emergencyContactName;
    }

    public Optional<StringFilter> optionalEmergencyContactName() {
        return Optional.ofNullable(emergencyContactName);
    }

    public StringFilter emergencyContactName() {
        if (emergencyContactName == null) {
            setEmergencyContactName(new StringFilter());
        }
        return emergencyContactName;
    }

    public void setEmergencyContactName(StringFilter emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public StringFilter getEmeregencyContactPhone() {
        return emeregencyContactPhone;
    }

    public Optional<StringFilter> optionalEmeregencyContactPhone() {
        return Optional.ofNullable(emeregencyContactPhone);
    }

    public StringFilter emeregencyContactPhone() {
        if (emeregencyContactPhone == null) {
            setEmeregencyContactPhone(new StringFilter());
        }
        return emeregencyContactPhone;
    }

    public void setEmeregencyContactPhone(StringFilter emeregencyContactPhone) {
        this.emeregencyContactPhone = emeregencyContactPhone;
    }

    public StringFilter getChronicdiseases() {
        return chronicdiseases;
    }

    public Optional<StringFilter> optionalChronicdiseases() {
        return Optional.ofNullable(chronicdiseases);
    }

    public StringFilter chronicdiseases() {
        if (chronicdiseases == null) {
            setChronicdiseases(new StringFilter());
        }
        return chronicdiseases;
    }

    public void setChronicdiseases(StringFilter chronicdiseases) {
        this.chronicdiseases = chronicdiseases;
    }

    public StringFilter getMedicalHistory() {
        return medicalHistory;
    }

    public Optional<StringFilter> optionalMedicalHistory() {
        return Optional.ofNullable(medicalHistory);
    }

    public StringFilter medicalHistory() {
        if (medicalHistory == null) {
            setMedicalHistory(new StringFilter());
        }
        return medicalHistory;
    }

    public void setMedicalHistory(StringFilter medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public LongFilter getAppointementId() {
        return appointementId;
    }

    public Optional<LongFilter> optionalAppointementId() {
        return Optional.ofNullable(appointementId);
    }

    public LongFilter appointementId() {
        if (appointementId == null) {
            setAppointementId(new LongFilter());
        }
        return appointementId;
    }

    public void setAppointementId(LongFilter appointementId) {
        this.appointementId = appointementId;
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
        final PatientCriteria that = (PatientCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(dateBirth, that.dateBirth) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(address, that.address) &&
            Objects.equals(bloodType, that.bloodType) &&
            Objects.equals(allergies, that.allergies) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(emergencyContactName, that.emergencyContactName) &&
            Objects.equals(emeregencyContactPhone, that.emeregencyContactPhone) &&
            Objects.equals(chronicdiseases, that.chronicdiseases) &&
            Objects.equals(medicalHistory, that.medicalHistory) &&
            Objects.equals(appointementId, that.appointementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nom,
            lastName,
            firstName,
            dateBirth,
            phone,
            email,
            address,
            bloodType,
            allergies,
            notes,
            emergencyContactName,
            emeregencyContactPhone,
            chronicdiseases,
            medicalHistory,
            appointementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNom().map(f -> "nom=" + f + ", ").orElse("") +
            optionalLastName().map(f -> "lastName=" + f + ", ").orElse("") +
            optionalFirstName().map(f -> "firstName=" + f + ", ").orElse("") +
            optionalDateBirth().map(f -> "dateBirth=" + f + ", ").orElse("") +
            optionalPhone().map(f -> "phone=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalAddress().map(f -> "address=" + f + ", ").orElse("") +
            optionalBloodType().map(f -> "bloodType=" + f + ", ").orElse("") +
            optionalAllergies().map(f -> "allergies=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalEmergencyContactName().map(f -> "emergencyContactName=" + f + ", ").orElse("") +
            optionalEmeregencyContactPhone().map(f -> "emeregencyContactPhone=" + f + ", ").orElse("") +
            optionalChronicdiseases().map(f -> "chronicdiseases=" + f + ", ").orElse("") +
            optionalMedicalHistory().map(f -> "medicalHistory=" + f + ", ").orElse("") +
            optionalAppointementId().map(f -> "appointementId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
