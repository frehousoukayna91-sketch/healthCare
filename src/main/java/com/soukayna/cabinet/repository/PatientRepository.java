package com.soukayna.cabinet.repository;

import com.soukayna.cabinet.domain.Patient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {
    interface PatientHistoryProjection {
        Integer getPatientId();

        String getFirstName();

        String getLastName();

        LocalDate getDateOfBirth();

        String getGender();

        String getPhone();

        String getEmail();

        String getBloodType();

        String getAllergies();

        String getMedicalConditions();

        Long getAppointmentId();

        LocalDate getAppointmentDate();

        LocalTime getAppointmentTime();

        String getAppointmentType();

        String getAppointmentStatus();

        String getReason();

        Long getPrescriptionId();

        LocalDate getPrescriptionDate();

        String getDiagnosis();

        String getMedications();

        Long getInvoiceId();

        String getInvoiceNumber();

        LocalDate getInvoiceDate();

        BigDecimal getInvoiceTotal();

        String getInvoiceStatus();
    }

    @Query(
        """
        select p
        from Patient p
        where lower(coalesce(p.firstName, '')) like lower(concat('%', :query, '%'))
           or lower(coalesce(p.lastName, '')) like lower(concat('%', :query, '%'))
           or lower(coalesce(p.nom, '')) like lower(concat('%', :query, '%'))
           or lower(coalesce(p.email, '')) like lower(concat('%', :query, '%'))
           or lower(coalesce(p.phone, '')) like lower(concat('%', :query, '%'))
        """
    )
    Page<Patient> search(@Param("query") String query, Pageable pageable);

    @Query(
        value = """
        SELECT
            p.id AS patientId,
            p.first_name AS firstName,
            p.last_name AS lastName,
            p.date_birth AS dateOfBirth,
            NULL::varchar AS gender,
            p.phone AS phone,
            p.email AS email,
            p.blood_type AS bloodType,
            p.allergies AS allergies,
            p.chronicdiseases AS medicalConditions,
            a.id AS appointmentId,
            a.appointement_date AS appointmentDate,
            a.appointement_time AS appointmentTime,
            CAST(a.type AS varchar) AS appointmentType,
            CAST(a.status AS varchar) AS appointmentStatus,
            a.reason_appointement AS reason,
            pr.id AS prescriptionId,
            pr.prescription_date AS prescriptionDate,
            pr.diagnosis AS diagnosis,
            STRING_AGG(DISTINCT pi.medication_name, ', ') AS medications,
            i.id AS invoiceId,
            i.invoice_number AS invoiceNumber,
            i.date AS invoiceDate,
            i.total AS invoiceTotal,
            CAST(i.status AS varchar) AS invoiceStatus
        FROM patient p
        LEFT JOIN appointement a ON a.patient_id = p.id
        LEFT JOIN prescription pr ON pr.patient_id = p.id
        LEFT JOIN prescription_item pi ON pi.prescription_id = pr.id
        LEFT JOIN invoice i ON i.patient_id = p.id
        WHERE p.id = :patientId
        GROUP BY
            p.id,
            p.first_name,
            p.last_name,
            p.date_birth,
            p.phone,
            p.email,
            p.blood_type,
            p.allergies,
            p.chronicdiseases,
            a.id,
            a.appointement_date,
            a.appointement_time,
            a.type,
            a.status,
            a.reason_appointement,
            pr.id,
            pr.prescription_date,
            pr.diagnosis,
            i.id,
            i.invoice_number,
            i.date,
            i.total,
            i.status
        ORDER BY a.appointement_date DESC NULLS LAST, pr.prescription_date DESC NULLS LAST, i.date DESC NULLS LAST
        """,
        nativeQuery = true
    )
    List<PatientHistoryProjection> findPatientHistoryByPatientId(@Param("patientId") Integer patientId);
}
