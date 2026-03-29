package com.soukayna.cabinet.service.impl;

import com.soukayna.cabinet.domain.Prescription;
import com.soukayna.cabinet.repository.PrescriptionRepository;
import com.soukayna.cabinet.service.PrescriptionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.soukayna.cabinet.domain.Prescription}.
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Prescription save(Prescription prescription) {
        LOG.debug("Request to save Prescription : {}", prescription);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription update(Prescription prescription) {
        LOG.debug("Request to update Prescription : {}", prescription);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Optional<Prescription> partialUpdate(Prescription prescription) {
        LOG.debug("Request to partially update Prescription : {}", prescription);

        return prescriptionRepository
            .findById(prescription.getId())
            .map(existingPrescription -> {
                if (prescription.getPatientId() != null) {
                    existingPrescription.setPatientId(prescription.getPatientId());
                }
                if (prescription.getPrescriptionDate() != null) {
                    existingPrescription.setPrescriptionDate(prescription.getPrescriptionDate());
                }
                if (prescription.getDiagnosis() != null) {
                    existingPrescription.setDiagnosis(prescription.getDiagnosis());
                }
                if (prescription.getFollowupDate() != null) {
                    existingPrescription.setFollowupDate(prescription.getFollowupDate());
                }
                if (prescription.getNotes() != null) {
                    existingPrescription.setNotes(prescription.getNotes());
                }

                return existingPrescription;
            })
            .map(prescriptionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prescription> findOne(Long id) {
        LOG.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
    }
}
