package com.soukayna.cabinet.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record PatientHistoryVM(
    @JsonProperty("patient_id") Integer patientId,
    @JsonProperty("first_name") String firstName,
    @JsonProperty("last_name") String lastName,
    @JsonProperty("date_of_birth") LocalDate dateOfBirth,
    @JsonProperty("gender") String gender,
    @JsonProperty("phone") String phone,
    @JsonProperty("email") String email,
    @JsonProperty("blood_type") String bloodType,
    @JsonProperty("allergies") String allergies,
    @JsonProperty("medical_conditions") String medicalConditions,
    @JsonProperty("appointment_id") Long appointmentId,
    @JsonProperty("appointment_date") LocalDate appointmentDate,
    @JsonProperty("appointment_time") LocalTime appointmentTime,
    @JsonProperty("appointment_type") String appointmentType,
    @JsonProperty("appointment_status") String appointmentStatus,
    @JsonProperty("reason") String reason,
    @JsonProperty("prescription_id") Long prescriptionId,
    @JsonProperty("prescription_date") LocalDate prescriptionDate,
    @JsonProperty("diagnosis") String diagnosis,
    @JsonProperty("medications") String medications,
    @JsonProperty("invoice_id") Long invoiceId,
    @JsonProperty("invoice_number") String invoiceNumber,
    @JsonProperty("invoice_date") LocalDate invoiceDate,
    @JsonProperty("invoice_total") BigDecimal invoiceTotal,
    @JsonProperty("invoice_status") String invoiceStatus
) {}
