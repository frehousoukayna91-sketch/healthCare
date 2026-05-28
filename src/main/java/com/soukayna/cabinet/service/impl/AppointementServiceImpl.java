package com.soukayna.cabinet.service.impl;

import com.soukayna.cabinet.domain.Appointement;
import com.soukayna.cabinet.domain.enumeration.statusAppointement;
import com.soukayna.cabinet.repository.AppointementRepository;
import com.soukayna.cabinet.service.AppointementService;
import com.soukayna.cabinet.web.rest.errors.BadRequestAlertException;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.soukayna.cabinet.domain.Appointement}.
 */
@Service
@Transactional
public class AppointementServiceImpl implements AppointementService {

    private static final Logger LOG = LoggerFactory.getLogger(AppointementServiceImpl.class);

    private static final String ENTITY_NAME = "appointement";
    private static final int DEFAULT_DURATION_MINUTES = 30;
    private static final EnumSet<statusAppointement> SLOT_FREEING_STATUSES = EnumSet.of(
        statusAppointement.CANCELED,
        statusAppointement.NO_SHOW
    );

    private final AppointementRepository appointementRepository;

    public AppointementServiceImpl(AppointementRepository appointementRepository) {
        this.appointementRepository = appointementRepository;
    }

    @Override
    public Appointement save(Appointement appointement) {
        LOG.debug("Request to save Appointement : {}", appointement);
        checkConflict(appointement);
        return appointementRepository.save(appointement);
    }

    @Override
    public Appointement update(Appointement appointement) {
        LOG.debug("Request to update Appointement : {}", appointement);
        checkConflict(appointement);
        return appointementRepository.save(appointement);
    }

    @Override
    public Optional<Appointement> partialUpdate(Appointement appointement) {
        LOG.debug("Request to partially update Appointement : {}", appointement);

        return appointementRepository
            .findById(appointement.getId())
            .map(existingAppointement -> {
                if (appointement.getId() != null) {
                    existingAppointement.setId(appointement.getId());
                }
                if (appointement.getDuration() != null) {
                    existingAppointement.setDuration(appointement.getDuration());
                }
                if (appointement.getStatus() != null) {
                    existingAppointement.setStatus(appointement.getStatus());
                }
                if (appointement.getType() != null) {
                    existingAppointement.setType(appointement.getType());
                }
                if (appointement.getAppointementDate() != null) {
                    existingAppointement.setAppointementDate(appointement.getAppointementDate());
                }
                if (appointement.getAppointementTime() != null) {
                    existingAppointement.setAppointementTime(appointement.getAppointementTime());
                }
                if (appointement.getReasonAppointement() != null) {
                    existingAppointement.setReasonAppointement(appointement.getReasonAppointement());
                }
                if (appointement.getNotes() != null) {
                    existingAppointement.setNotes(appointement.getNotes());
                }

                return existingAppointement;
            })
            .map(merged -> {
                checkConflict(merged);
                return appointementRepository.save(merged);
            });
    }

    private void checkConflict(Appointement candidate) {
        if (candidate.getAppointementDate() == null || candidate.getAppointementTime() == null) {
            return;
        }
        if (candidate.getStatus() != null && SLOT_FREEING_STATUSES.contains(candidate.getStatus())) {
            return;
        }

        LocalTime candidateStart = candidate.getAppointementTime();
        LocalTime candidateEnd = candidateStart.plusMinutes(durationOrDefault(candidate.getDuration()));

        boolean hasConflict = appointementRepository
            .findActiveOnDate(candidate.getAppointementDate(), SLOT_FREEING_STATUSES)
            .stream()
            .filter(existing -> !existing.getId().equals(candidate.getId()))
            .filter(existing -> existing.getAppointementTime() != null)
            .anyMatch(existing -> {
                LocalTime existingStart = existing.getAppointementTime();
                LocalTime existingEnd = existingStart.plusMinutes(durationOrDefault(existing.getDuration()));
                return candidateStart.isBefore(existingEnd) && existingStart.isBefore(candidateEnd);
            });

        if (hasConflict) {
            throw new BadRequestAlertException("Appointment slot overlaps with an existing one", ENTITY_NAME, "appointementconflict");
        }
    }

    private static int durationOrDefault(Integer duration) {
        return duration != null && duration > 0 ? duration : DEFAULT_DURATION_MINUTES;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Appointement> findOne(Long id) {
        LOG.debug("Request to get Appointement : {}", id);
        return appointementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Appointement : {}", id);
        appointementRepository.deleteById(id);
    }
}
