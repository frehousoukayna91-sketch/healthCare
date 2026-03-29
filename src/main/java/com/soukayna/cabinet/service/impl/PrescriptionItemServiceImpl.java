package com.soukayna.cabinet.service.impl;

import com.soukayna.cabinet.domain.PrescriptionItem;
import com.soukayna.cabinet.repository.PrescriptionItemRepository;
import com.soukayna.cabinet.service.PrescriptionItemService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.soukayna.cabinet.domain.PrescriptionItem}.
 */
@Service
@Transactional
public class PrescriptionItemServiceImpl implements PrescriptionItemService {

    private static final Logger LOG = LoggerFactory.getLogger(PrescriptionItemServiceImpl.class);

    private final PrescriptionItemRepository prescriptionItemRepository;

    public PrescriptionItemServiceImpl(PrescriptionItemRepository prescriptionItemRepository) {
        this.prescriptionItemRepository = prescriptionItemRepository;
    }

    @Override
    public PrescriptionItem save(PrescriptionItem prescriptionItem) {
        LOG.debug("Request to save PrescriptionItem : {}", prescriptionItem);
        return prescriptionItemRepository.save(prescriptionItem);
    }

    @Override
    public PrescriptionItem update(PrescriptionItem prescriptionItem) {
        LOG.debug("Request to update PrescriptionItem : {}", prescriptionItem);
        return prescriptionItemRepository.save(prescriptionItem);
    }

    @Override
    public Optional<PrescriptionItem> partialUpdate(PrescriptionItem prescriptionItem) {
        LOG.debug("Request to partially update PrescriptionItem : {}", prescriptionItem);

        return prescriptionItemRepository
            .findById(prescriptionItem.getId())
            .map(existingPrescriptionItem -> {
                if (prescriptionItem.getDuration() != null) {
                    existingPrescriptionItem.setDuration(prescriptionItem.getDuration());
                }
                if (prescriptionItem.getFrequency() != null) {
                    existingPrescriptionItem.setFrequency(prescriptionItem.getFrequency());
                }
                if (prescriptionItem.getMedicationName() != null) {
                    existingPrescriptionItem.setMedicationName(prescriptionItem.getMedicationName());
                }
                if (prescriptionItem.getMedicationDosage() != null) {
                    existingPrescriptionItem.setMedicationDosage(prescriptionItem.getMedicationDosage());
                }
                if (prescriptionItem.getInstructions() != null) {
                    existingPrescriptionItem.setInstructions(prescriptionItem.getInstructions());
                }

                return existingPrescriptionItem;
            })
            .map(prescriptionItemRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PrescriptionItem> findOne(Long id) {
        LOG.debug("Request to get PrescriptionItem : {}", id);
        return prescriptionItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PrescriptionItem : {}", id);
        prescriptionItemRepository.deleteById(id);
    }
}
