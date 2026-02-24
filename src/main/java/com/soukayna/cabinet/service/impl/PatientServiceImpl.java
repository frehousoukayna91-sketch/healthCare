package com.soukayna.cabinet.service.impl;

import com.soukayna.cabinet.domain.Patient;
import com.soukayna.cabinet.repository.PatientRepository;
import com.soukayna.cabinet.service.PatientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.soukayna.cabinet.domain.Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private static final Logger LOG = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        LOG.debug("Request to save Patient : {}", patient);
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        LOG.debug("Request to update Patient : {}", patient);
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> partialUpdate(Patient patient) {
        LOG.debug("Request to partially update Patient : {}", patient);

        return patientRepository
            .findById(patient.getId())
            .map(existingPatient -> {
                if (patient.getNom() != null) {
                    existingPatient.setNom(patient.getNom());
                }
                if (patient.getLastName() != null) {
                    existingPatient.setLastName(patient.getLastName());
                }
                if (patient.getFirstName() != null) {
                    existingPatient.setFirstName(patient.getFirstName());
                }
                if (patient.getDateBirth() != null) {
                    existingPatient.setDateBirth(patient.getDateBirth());
                }
                if (patient.getPhone() != null) {
                    existingPatient.setPhone(patient.getPhone());
                }
                if (patient.getEmail() != null) {
                    existingPatient.setEmail(patient.getEmail());
                }
                if (patient.getAddress() != null) {
                    existingPatient.setAddress(patient.getAddress());
                }
                if (patient.getBloodType() != null) {
                    existingPatient.setBloodType(patient.getBloodType());
                }
                if (patient.getAllergies() != null) {
                    existingPatient.setAllergies(patient.getAllergies());
                }
                if (patient.getNotes() != null) {
                    existingPatient.setNotes(patient.getNotes());
                }
                if (patient.getEmergencyContactName() != null) {
                    existingPatient.setEmergencyContactName(patient.getEmergencyContactName());
                }
                if (patient.getEmeregencyContactPhone() != null) {
                    existingPatient.setEmeregencyContactPhone(patient.getEmeregencyContactPhone());
                }
                if (patient.getChronicdiseases() != null) {
                    existingPatient.setChronicdiseases(patient.getChronicdiseases());
                }
                if (patient.getMedicalHistory() != null) {
                    existingPatient.setMedicalHistory(patient.getMedicalHistory());
                }

                return existingPatient;
            })
            .map(patientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findOne(Integer id) {
        LOG.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        LOG.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}
